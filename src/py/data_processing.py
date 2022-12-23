import speechbrain as sb
from speechbrain.dataio.dataio import read_audio
#from IPython.display import Audio
import os
from scipy.io.wavfile import write
import numpy as np

#separation
from speechbrain.pretrained import SepformerSeparation as separator
from speechbrain.pretrained import SpeakerRecognition

import parselmouth 
from parselmouth.praat import call

import sys
import soundfile

from pathlib import WindowsPath

old_open = soundfile.SoundFile._open
def patched_open(self, file, mode_int, closefd):
    if isinstance(file, WindowsPath):
            file = str(file.resolve())
    return old_open(self, file, mode_int, closefd)
soundfile.SoundFile._open = patched_open

def measurePitch(voiceID, f0min, f0max, unit):
    sound = parselmouth.Sound(voiceID) # read the sound
    
    duration = call(sound, "Get total duration") # duration
    pitch = call(sound, "To Pitch", 0.0, f0min, f0max) #create a praat pitch object

    pitch_values = pitch.selected_array['frequency']
    pitch_values[pitch_values==0] = np.nan
    Fhi, Flo = 0, 1e9
    for p in pitch_values:
        if p != np.nan:
            Fhi = max(Fhi, p)
            Flo = min(Flo, p)

    meanF0 = call(pitch, "Get mean", 0, 0, unit) # get mean pitch
    stdevF0 = call(pitch, "Get standard deviation", 0 ,0, unit) # get standard deviation
    harmonicity = call(sound, "To Harmonicity (cc)", 0.01, f0min, 0.1, 1.0)
    hnr = call(harmonicity, "Get mean", 0, 0)
    pointProcess = call(sound, "To PointProcess (periodic, cc)", f0min, f0max)
    localJitter = call(pointProcess, "Get jitter (local)", 0, 0, 0.0001, 0.02, 1.3)
    localabsoluteJitter = call(pointProcess, "Get jitter (local, absolute)", 0, 0, 0.0001, 0.02, 1.3)
    rapJitter = call(pointProcess, "Get jitter (rap)", 0, 0, 0.0001, 0.02, 1.3)
    ppq5Jitter = call(pointProcess, "Get jitter (ppq5)", 0, 0, 0.0001, 0.02, 1.3)
    ddpJitter = call(pointProcess, "Get jitter (ddp)", 0, 0, 0.0001, 0.02, 1.3)
    localShimmer =  call([sound, pointProcess], "Get shimmer (local)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    localdbShimmer = call([sound, pointProcess], "Get shimmer (local_dB)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    apq3Shimmer = call([sound, pointProcess], "Get shimmer (apq3)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    apq5Shimmer = call([sound, pointProcess], "Get shimmer (apq5)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    apq11Shimmer =  call([sound, pointProcess], "Get shimmer (apq11)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    ddaShimmer = call([sound, pointProcess], "Get shimmer (dda)", 0, 0, 0.0001, 0.02, 1.3, 1.6) / 1000
    
    """
    return (duration, meanF0, stdevF0, hnr, localJitter, localabsoluteJitter, rapJitter, ppq5Jitter, ddpJitter, 
            localShimmer, localdbShimmer, apq3Shimmer, apq5Shimmer, apq11Shimmer, ddaShimmer)

    status: 似乎可以忽略
    其他: praat沒這個參數
    """

    nhr, status, RPDE, DFA, spread1, spread2, D2, PPE = (-1,) * 8
    
    return (meanF0, Fhi, Flo, localJitter, localabsoluteJitter, rapJitter, ppq5Jitter, ddpJitter, 
            localShimmer, localdbShimmer, apq3Shimmer, apq5Shimmer, apq11Shimmer, ddaShimmer, hnr)

model = separator.from_hparams(source="speechbrain/sepformer-wsj02mix", savedir='pretrained_models/sepformer-wsj02mix')
verification = SpeakerRecognition.from_hparams(source="speechbrain/spkrec-ecapa-voxceleb", savedir="pretrained_models/spkrec-ecapa-voxceleb")

#input
path = sys.argv[-1].replace("\\","/")
if not os.path.exists(path):
    exit(1)
#path = os.getcwd()+"/test_data_raw/test_set_subject_26.wma"

spearated = model.separate_file(path)

file_name = ".".join(path.split("/")[-1].split(".")[:-1])
scores = [0,0]
for i in range(2):

    spearated_np =  np.array(spearated[:,:,i].detach().cpu().squeeze())
    write(file_name+f'-{i}.wav', 8000, np.int16(spearated_np/np.max(np.abs(spearated_np)) * 32767))

#    score, prediction = verification.verify_files(file_name+f'-{i}.wav', f'test.wav')
#    scores[i] = score

#if max(scores) < 0.7:
#    exit(2)

i = scores.index(max(scores))

result = measurePitch(parselmouth.Sound(file_name+f'-{i}.wav'), 75, 300, "Hertz")
print(*result)

result = measurePitch(parselmouth.Sound(file_name+f'-{1-i}.wav'), 75, 300, "Hertz")
print(*result)

for i in range(2):
    os.remove(file_name+f'-{1-i}.wav')
os.remove(file_name+'.wav')