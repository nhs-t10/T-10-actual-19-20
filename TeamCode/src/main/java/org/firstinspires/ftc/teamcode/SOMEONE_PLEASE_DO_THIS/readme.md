1. Install OpenCV from [here](https://sourceforge.net/projects/opencvlibrary/files/opencv-android/) (Download the folder, not the executable)
2. Go to Android Studio file menu and follow the path as shown `File > New > Import Module`
![Reference Image](https://inducesmile.com/wp-content/uploads/2018/10/importmodule-1.png "Reference Image")
Select the `java` folder in the OpenCV folder
3. Make sure gradle is synced and the module is loaded
4. Click on the `File > Project Structure` and then dependencies and press the `+` button and choose OpenCV
5. Put `sdk/native` from the OpenCV folder into `src > main > libs` of the project folder
6. Rename `libs` to `jniLibs`, I guess?

This guide was from [here](https://inducesmile.com/android/how-to-install-and-use-opencv-in-android/). hopefully it works