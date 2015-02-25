/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dialogwidget;

import java.text.NumberFormat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyProgressDialog extends Dialog {

	private static final String TAG = "MyMyProgressDialog";

	/**
	 * Creates a MyProgressDialog with a circular, spinning progress bar. This is
	 * the default.
	 */
	public static final int STYLE_SPINNER = 0;

	/**
	 * Creates a MyProgressDialog with a horizontal progress bar.
	 */
	public static final int STYLE_HORIZONTAL = 1;

	private Context mContext;
	private TextView mTvTitle;
	private Button mBtnClose;
	private ProgressBar mProgress;
	private TextView mMessageView;

	private int mProgressStyle = STYLE_SPINNER;
	private TextView mProgressNumber;
	private String mProgressNumberFormat;
	private TextView mProgressPercent;
	private NumberFormat mProgressPercentFormat;

	private int mMax;
	private int mProgressVal;
	private int mSecondaryProgressVal;
	private String mTitleStrVal;
	private int mIncrementBy;
	private int mIncrementSecondaryBy;
	private Drawable mProgressDrawable;
	private Drawable mIndeterminateDrawable;
	private CharSequence mMessage;
	private boolean mIndeterminate;

	private boolean mHasStarted;
	private Handler mViewUpdateHandler;

	public MyProgressDialog(Context context) {
//		super(context);
		super(context, R.style.Dialog);
		mContext = context;
		initFormats();
		
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		initFormats();
	}

	private void initFormats() {
		mProgressNumberFormat = "%1d/%2d";
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		mProgressPercentFormat.setMaximumFractionDigits(0);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		Log.e(TAG, "super.show()"+mProgressVal+"/"+mMax+ " tilte"+ mTitleStrVal+ " msg="+ mMessage);
	}
	
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		mTitleStrVal=title.toString();
		if(mTvTitle!=null){
			mTvTitle.setText(TextUtils.isEmpty(mTitleStrVal)==true?"":mTitleStrVal);
		}
	}
	

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message) {
		return show(context, title, message, false);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, false, null);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		Log.e(TAG, "show()");
		MyProgressDialog dialog = new MyProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.show();
		return dialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate()");
		LayoutInflater inflater = LayoutInflater.from(mContext);
		// TypedArray a = mContext.obtainStyledAttributes(null,
		// com.android.internal.R.styleable.AlertDialog,
		// com.android.internal.R.attr.alertDialogStyle, 0);
		if (mProgressStyle == STYLE_HORIZONTAL) {

			/*
			 * Use a separate handler to update the text views as they must be
			 * updated on the same thread that created them.
			 */
			mViewUpdateHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);

					/* Update the number and percent */
					int progress = mProgress.getProgress();
					int max = mProgress.getMax();
					if (mProgressNumberFormat != null) {
						String format = mProgressNumberFormat;
						mProgressNumber.setText(String.format(format, progress,
								max));
					} else {
						mProgressNumber.setText("");
					}
					if (mProgressPercentFormat != null) {
						double percent = (double) progress / (double) max;
						SpannableString tmp = new SpannableString(
								mProgressPercentFormat.format(percent));
						tmp.setSpan(new StyleSpan(
								android.graphics.Typeface.BOLD), 0, tmp
								.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						mProgressPercent.setText(tmp);
					} else {
						mProgressPercent.setText("");
					}
				}
			};
			View view = inflater.inflate(
					R.layout.dialog_progress_horizontal_layout, null);
			mTvTitle = (TextView) view.findViewById(R.id.title);
			mBtnClose = (Button) view.findViewById(R.id.btn_close);
			mProgress = (ProgressBar) view.findViewById(R.id.progress);
			mProgressNumber = (TextView) view
					.findViewById(R.id.progress_number);
			mMessageView = (TextView) view.findViewById(R.id.message);
			mProgressPercent = (TextView) view
					.findViewById(R.id.progress_percent);
			setContentView(view);
		} else {
			View view = inflater.inflate(
					R.layout.dialog_progress_sipiner_layout, null);
			mTvTitle = (TextView) view.findViewById(R.id.title);
			mBtnClose = (Button) view.findViewById(R.id.btn_close);
			mProgress = (ProgressBar) view.findViewById(R.id.progress);
			mMessageView = (TextView) view.findViewById(R.id.message);
			setContentView(view);
		}
		// a.recycle();
		if (mMax > 0) {
			setMax(mMax);
		}
		if (mProgressVal > 0) {
			setProgress(mProgressVal);
		}
		if (mSecondaryProgressVal > 0) {
			setSecondaryProgress(mSecondaryProgressVal);
		}
		if (mIncrementBy > 0) {
			incrementProgressBy(mIncrementBy);
		}
		if (mIncrementSecondaryBy > 0) {
			incrementSecondaryProgressBy(mIncrementSecondaryBy);
		}
		if (mProgressDrawable != null) {
			setProgressDrawable(mProgressDrawable);
		}
		if (mIndeterminateDrawable != null) {
			setIndeterminateDrawable(mIndeterminateDrawable);
		}
		if (mMessage != null) {
			setMessage(mMessage);
		}
		if(mBtnClose!=null){
			mBtnClose.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cancel();
				}
			});
		}
		
		setIndeterminate(mIndeterminate);
		onProgressChanged();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "onStart()");
		mHasStarted = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop()");
		mHasStarted = false;
	}

	public void setProgress(int value) {
		if (mHasStarted) {
			if (mProgress != null) {
			mProgress.setProgress(value);
			}
			onProgressChanged();
		} else {
			mProgressVal = value;
		}
	}

	public void setSecondaryProgress(int secondaryProgress) {
		if (mProgress != null) {
			mProgress.setSecondaryProgress(secondaryProgress);
			onProgressChanged();
		} else {
			mSecondaryProgressVal = secondaryProgress;
		}
	}

	public int getProgress() {
		if (mProgress != null) {
			return mProgress.getProgress();
		}
		return mProgressVal;
	}

	public int getSecondaryProgress() {
		if (mProgress != null) {
			return mProgress.getSecondaryProgress();
		}
		return mSecondaryProgressVal;
	}

	public int getMax() {
		if (mProgress != null) {
			return mProgress.getMax();
		}
		return mMax;
	}

	public void setMax(int max) {
		if (mProgress != null) {
			mProgress.setMax(max);
			onProgressChanged();
		} else {
			mMax = max;
		}
	}

	public void incrementProgressBy(int diff) {
		if (mProgress != null) {
			mProgress.incrementProgressBy(diff);
			onProgressChanged();
		} else {
			mIncrementBy += diff;
		}
	}

	public void incrementSecondaryProgressBy(int diff) {
		if (mProgress != null) {
			mProgress.incrementSecondaryProgressBy(diff);
			onProgressChanged();
		} else {
			mIncrementSecondaryBy += diff;
		}
	}

	public void setProgressDrawable(Drawable d) {
		if (mProgress != null) {
			mProgress.setProgressDrawable(d);
		} else {
			mProgressDrawable = d;
		}
	}

	public void setIndeterminateDrawable(Drawable d) {
		if (mProgress != null) {
			mProgress.setIndeterminateDrawable(d);
		} else {
			mIndeterminateDrawable = d;
		}
	}

	public void setIndeterminate(boolean indeterminate) {
		if (mProgress != null) {
			mProgress.setIndeterminate(indeterminate);
		} else {
			mIndeterminate = indeterminate;
		}
	}

	public boolean isIndeterminate() {
		if (mProgress != null) {
			return mProgress.isIndeterminate();
		}
		return mIndeterminate;
	}

	public void setMessage(CharSequence message) {
		if (mProgress != null) {
//			 if (mProgressStyle == STYLE_HORIZONTAL) {
//			 super.setMessage(message);
//			 } else {
//			 mMessageView.setText(message);
//			 }
			if (mMessageView != null) {
				mMessageView.setText(TextUtils.isEmpty(message)==true?"":message);
			} else {
				mMessage = message;
			}
		} else {
			mMessage = message;
		}
	}

	public void setProgressStyle(int style) {
		mProgressStyle = style;
	}

	/**
	 * Change the format of the small text showing current and maximum units of
	 * progress. The default is "%1d/%2d". Should not be called during the
	 * number is progressing.
	 * 
	 * @param format
	 *            A string passed to {@link String#format String.format()}; use
	 *            "%1d" for the current number and "%2d" for the maximum. If
	 *            null, nothing will be shown.
	 */
	public void setProgressNumberFormat(String format) {
		mProgressNumberFormat = format;
		onProgressChanged();
	}

	/**
	 * Change the format of the small text showing the percentage of progress.
	 * The default is {@link NumberFormat#getPercentInstance()
	 * NumberFormat.getPercentageInstnace().} Should not be called during the
	 * number is progressing.
	 * 
	 * @param format
	 *            An instance of a {@link NumberFormat} to generate the
	 *            percentage text. If null, nothing will be shown.
	 */
	public void setProgressPercentFormat(NumberFormat format) {
		mProgressPercentFormat = format;
		onProgressChanged();
	}

	private void onProgressChanged() {
		if (mProgressStyle == STYLE_HORIZONTAL) {
			if (mViewUpdateHandler != null
					&& !mViewUpdateHandler.hasMessages(0)) {
				mViewUpdateHandler.sendEmptyMessage(0);
			}
		}
	}
}
