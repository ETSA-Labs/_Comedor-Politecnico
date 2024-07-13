package com.espoch.comedor.shared.services

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentActivity
import com.espoch.comedor.shared.R
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
 * It provides a method to generate QR codes as Drawable objects.
 */
class QrService {
    companion object {
        // Options for QR code generation
        private var options: QrVectorOptions? = null

        /**
         * Generates a QR code as a Drawable.
         * This method requires the options to be initialized if not already done.
         *
         * @param activity The FragmentActivity used to retrieve the context for colors.
         * @param content The content to encode in the QR code.
         * @return A Drawable representing the QR code.
         * @throws IllegalArgumentException if the context cannot be retrieved.
         */
        fun generate(activity: FragmentActivity, content: String): Drawable {
            // Builds the options if needed
            if (options == null) {
                options = QrVectorOptions.Builder()
                    .setPadding(.2f) // Set padding for the QR code
                    .setBackground(
                        QrVectorBackground(
                            color = QrVectorColor.Solid(
                                getColor(activity, R.color.window) // Set background color
                            )
                        )
                    )
                    .setColors(
                        QrVectorColors(
                            dark = QrVectorColor.Solid(
                                getColor(activity, R.color.fore_medium) // Set dark color
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

            // Create QrData with the provided content
            val data = QrData.Text(content)

            // Generate and return the QR code as a Drawable
            return QrCodeDrawable(data, options!!)
        }
    }
}
