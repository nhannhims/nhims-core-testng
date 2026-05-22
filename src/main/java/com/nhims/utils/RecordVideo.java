package com.nhims.utils;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

public class RecordVideo extends ScreenRecorder {
	private static final ThreadLocal<ScreenRecorder> screenRecorderThread = new ThreadLocal<>();
	private static final String videoFolder = HDate.formatDate("yyyy_MM_dd_HHmmss");
	private final String name;

	/**
	 * Constructor for RecordVideo extending ScreenRecorder.
	 *
	 * @param cfg          graphics configuration of the screen
	 * @param captureArea  the rectangle area of the screen to capture
	 * @param fileFormat   the file format for storing the recording (e.g. AVI)
	 * @param screenFormat the video format for screen capture
	 * @param mouseFormat  the video format for mouse cursor capture
	 * @param audioFormat  the audio format for sound capture
	 * @param movieFolder  the directory to save the output video file
	 * @param name         the custom name for the video file
	 * @throws IOException  if an I/O error occurs
	 * @throws AWTException if the graphics environment does not support screen capture
	 */
	public RecordVideo(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
			Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;
	}

	/**
	 * Overrides the creation of the output movie file to use a custom naming scheme.
	 *
	 * @param fileFormat the output file format
	 * @return the output file descriptor
	 * @throws IOException if a folder cannot be created or file path is invalid
	 */
	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {
		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new RuntimeException("[" + movieFolder + "] is not a directory");
		}
		return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));
	}

	/**
	 * Starts recording the screen and saves it as an AVI file named after the method.
	 * Stores the active recorder in a thread-local variable screenRecorderThread.
	 *
	 * @param methodName the name of the test method being recorded
	 */
	public static void startRecord(String methodName) {
		File file = new File("./test-reports/videos/" + videoFolder + "/");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		Rectangle captureSize = new Rectangle(0, 0, width, height);
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		try {
			ScreenRecorder recorder = new RecordVideo(gc, captureSize,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, file, methodName);
			screenRecorderThread.set(recorder);
			recorder.start();
		} catch (IOException | AWTException e) {
			Logger.error("Can not start record video: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Stops the screen recorder for the current thread and removes it from ThreadLocal.
	 */
	public static void stopRecord() {
		try {
			ScreenRecorder recorder = screenRecorderThread.get();
			if (recorder != null) {
				recorder.stop();
				screenRecorderThread.remove();
			}
		} catch (IOException e) {
			Logger.error("Can not stop record video: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
