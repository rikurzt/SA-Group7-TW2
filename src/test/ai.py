import pickle
from sklearn import svm
import numpy as np
from sklearn.preprocessing import StandardScaler

import sys

inp = [float(i) for i in sys.argv[-15:]]

def predict(input_data):
    input_data_as_numpy_array = np.asarray(input_data)

    # reshape the numpy array
    input_data_reshaped = input_data_as_numpy_array.reshape(1,-1)

    # standardize the data
    std_data = scaler.transform(input_data_reshaped)

    prediction = model.predict(std_data)
    """
    print(prediction)

    if (prediction[0] == 0):
      print("The Person does not have Parkinson's Disease")

    else:
      print("The Person has Parkinson's")
    """
    print(str(prediction[0] != 0).lower())

with open("model.data","rb") as file:
    model : svm.SVC = pickle.load(file)
with open("scaler.data","rb") as file:
    scaler : StandardScaler = pickle.load(file)

predict(inp)