/*
 * Copyright 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.test.mycompose.ocr

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

/**
 * Analyzes the frames passed in from the camera and returns any detected text within the requested
 * crop region.
 */
class TextAnalyzer(private val context: Context) {

    private val detector = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    fun analyze(bitmap: Bitmap) {
        recognizeTextOnDevice(bitmap)
    }

    private fun recognizeTextOnDevice(bitmap: Bitmap) { // Pass image to an ML Kit Vision API
        detector.process(InputImage.fromBitmap(bitmap, 0)).addOnSuccessListener { visionText -> // Task completed successfully
            Log.d("TEMP", "****************** start **************************")
            Log.d("TEMP", "text:${visionText.text}")
            visionText.textBlocks.forEachIndexed { index, it ->
                Log.d("TEMP", "textBlocks textBlocks:$index")
                it.lines.forEachIndexed { index, it ->
                    Log.d("TEMP", "text line:$index")
                    it.elements.forEach {
                        Log.d(
                            "TEMP",
                            "elements text:${it.text}  boundingBox:${it.boundingBox}  cornerPoints:${it.cornerPoints}  recognizedLanguage:${it.recognizedLanguage}")
                    }
                }
            }
            Log.d("TEMP", "****************** end **************************")
        }.addOnFailureListener { exception -> // Task failed with an exception
            Log.e(TAG, "Text recognition error", exception)
            val message = getErrorMessage(exception)
            message?.let {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getErrorMessage(exception: Exception): String? {
        val mlKitException = exception as? MlKitException ?: return exception.message
        return if (mlKitException.errorCode == MlKitException.UNAVAILABLE) {
            "Waiting for text recognition model to be downloaded"
        } else exception.message
    }

    companion object {
        private const val TAG = "TextAnalyzer"
    }
}