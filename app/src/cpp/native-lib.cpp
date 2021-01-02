//
// Created by Ardanto Finkan Septa on 07/12/2020.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_ardantofinkansepta_catathutang_MainActivity_stringFromJNI( JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello, Welcome to the App!";
    return env->NewStringUTF(hello.c_str());
}

//Addition function
extern "C" JNIEXPORT jint JNICALL
Java_id_ac_ui_cs_mobileprogramming_ardantofinkansepta_catathutang_viewmodel_ListTransactionViewModel_timesminusone( JNIEnv *env, jobject, jint x) {

    //return an integer
    return x * -1;
}
