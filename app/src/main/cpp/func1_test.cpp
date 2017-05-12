#include <opencv2/opencv.hpp>
#include <jni.h>
#include <assert.h>
#include <dlfcn.h>
#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <android/log.h>

#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

void myNativeMethod(JNIEnv *env, jclass pJclass) {
    jmethodID method = env->GetMethodID(pJclass, "helloFromJava", "()V");
    env->CallVoidMethod(pJclass, method);
}

using namespace cv;

bool test_func(JNIEnv *env, jclass pJclass) {
    // load test image
    Mat img = imread("/storage/emulated/0/test_opencv.jpg");
    bool b = img.empty();
    if (b) { __android_log_print(ANDROID_LOG_DEBUG, "eee", "not read"); }
    else { __android_log_print(ANDROID_LOG_DEBUG, "eee", "read"); }
    // blur
    blur(img, img, Size(5, 5));
    // save test image
    imwrite("/storage/emulated/0/test_opencv_blur.jpg", img);
    return true;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_alw_myapplication_JNIUtils_ccc(JNIEnv *env, jclass type) {
    jmethodID method = env->GetMethodID(type, "helloFromJava", "()V");
    env->CallVoidMethod(type, method);
    test_func(env, type);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL Java_com_example_alw_myapplication_JNIUtils_bbb() {

}

