package com.espoch.comedor.services

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.R
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes

/**
 * QrService is a utility class for generating QR codes with specific vector options.
 * It provides methods to initialize the options and generate QR codes as Drawable objects.
 */
class QrService {
    companion object {
        // Options for QR code generation
        private var options: QrVectorOptions? = null

        /**
         * Initializes the QR code options using the provided activity's context.
         * This method must be called before generating a QR code.
         *
         * @param activity The FragmentActivity used to retrieve the context.
         * @throws IllegalArgumentException if the context cannot be retrieved.
         */
        fun initialize(activity: FragmentActivity) {
            // Retrieve the context using ContextService
            val context = ContextService.get("Main")
                ?: throw IllegalArgumentException("Context not found")

            // Configure the QR code options using QrVectorOptions.Builder
            options = QrVectorOptions.Builder()
                .setPadding(.2f) // Set padding for the QR code
                .setBackground(
                    QrVectorBackground(
                        color = QrVectorColor.Solid(
                            getColor(context, R.color.window) // Set background color
                        )
                    )
                )
                .setColors(
                    QrVectorColors(
                        dark = QrVectorColor.Solid(
                            getColor(context, R.color.fore_medium) // Set dark color
                        )
                    )
                )
                .setShapes(
                    QrVectorShapes(
                        darkPixel = QrVectorPixelShape.RoundCorners(.5f), // Set shape for dark pixels
                        ball = QrVectorBallShape.RoundCorners(.25f), // Set shape for balls
                        frame = QrVectorFrameShape.RoundCorners(.25f), // Set shape for frames
                    )
                )
                .build()
        }

        /**
         * Generates a QR code as a Drawable.
         * This method requires the options to be initialized first.
         *
         * @param content The content to encode in the QR code.
         * @return A Drawable representing the QR code.
         * @throws IllegalArgumentException if the options have not been initialized.
         */
        fun generate(content: String): Drawable {
            if (options == null) {
                throw IllegalArgumentException("initialize(...) method must be called first.")
            }

            // Create QrData with the provided content
            val data = QrData.Text(content)

            // Generate and return the QR code as a Drawable
            return QrCodeDrawable(data, options!!)
        }
    }
}

