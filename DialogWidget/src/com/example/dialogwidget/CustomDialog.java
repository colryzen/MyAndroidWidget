package com.example.dialogwidget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		//********ListView相关
		 public DialogInterface.OnClickListener mOnClickListener;
		 public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
		 public boolean mIsSingleChoice;
		 public boolean mIsMultiChoice;
	     public int mCheckedItem = -1;
	     public String mLabelColumn;
	     public String mIsCheckedColumn;
	     public ListAdapter mAdapter;
		 public CharSequence[] mItems;
		 public Cursor mCursor;
		//********
		 private View mView;
		
		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		 public Builder setMessage(CharSequence message) {
			 this.message = message.toString();
			 return this;
		 }

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		
		public Builder setView(View view) {
	        mView = view;
	        return this;
	    }

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		
		
		
		//****************************
		
		 public Builder setAdapter(final ListAdapter adapter, final OnClickListener listener) {
	            mAdapter = adapter;
	            mOnClickListener = listener;
	            return this;
	        }
		 
		 
		  public Builder setSingleChoiceItems(int itemsId, int checkedItem, 
	                final OnClickListener listener) {
	            mItems = context.getResources().getTextArray(itemsId);
	            mOnClickListener = listener;
	            mCheckedItem = checkedItem;
	            mIsSingleChoice = true;
	            return this;
	        }
		 
		  public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
	            mItems = items;
	            mOnClickListener = listener;
	            mCheckedItem = checkedItem;
	            mIsSingleChoice = true;
	            return this;
	        } 
		 
		  public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, 
	                final OnClickListener listener) {
	            mCursor = cursor;
	            mOnClickListener = listener;
	            mCheckedItem = checkedItem;
	            mLabelColumn = labelColumn;
	            mIsSingleChoice = true;
	            return this;
	        }
		 
		 public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, final OnClickListener listener) {
	            mAdapter = adapter;
	            mOnClickListener = listener;
	            mCheckedItem = checkedItem;
	            mIsSingleChoice = true;
	            return this;
	        }
	        
	        /**
	         * Sets a listener to be invoked when an item in the list is selected.
	         * 
	         * @param listener The listener to be invoked.
	         * @see AdapterView#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)
	         *
	         * @return This Builder object to allow for chaining of calls to set methods
	         */
	        public Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
//	            P.mOnItemSelectedListener = listener;
	            return this;
	        }
		 
		
		//**********************************
		
		
		 public CustomDialog show() {
			    CustomDialog dialog = create();
	            dialog.show();
	            return dialog;
	        }
		

		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,
					R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			//关闭按钮
			Button btnClose= ((Button) layout.findViewById(R.id.btn_close));
			btnClose.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				((Button) layout.findViewById(R.id.positiveButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								if (positiveButtonClickListener != null) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}else{
									//默认隐藏
									dialog.dismiss();
								}
							}
						});

			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									if (negativeButtonClickListener != null) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
									}else {
										dialog.dismiss();
									}
								}
							});
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.message))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.message)).addView(
						contentView, new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			
			//显示listView数据
			 if ((mItems != null) || (mCursor != null) || (mAdapter != null)) {
	                createListView(dialog,layout);
	            }
			//显示Progress
			 if(mView!=null){
				 createProgress(dialog, layout);
			 }
			
			dialog.setContentView(layout);
			return dialog;
		}

		
		private void createListView(final CustomDialog dialog,View view) {

			 final ListView listView = (ListView)view.findViewById(R.id.lv_list);
			 view.findViewById(R.id.message).setVisibility(View.GONE);
			 listView.setVisibility(View.VISIBLE);
			 ListAdapter adapter=null;
            int layout = mIsSingleChoice 
                    ? R.layout.dialog_singlechoice_layout : R.layout.dialog_select_dialog_item;
            if (mCursor == null) {
                adapter = (mAdapter != null) ? mAdapter
                        : new ArrayAdapter<CharSequence>(context, layout, android.R.id.text1, mItems);
            } else {
                adapter = new SimpleCursorAdapter(context, layout, mCursor, new String[]{mLabelColumn}, new int[]{android.R.id.text1});
            }
			 
	         if (mOnClickListener != null) {
	                listView.setOnItemClickListener(new OnItemClickListener() {
	                    @SuppressWarnings("rawtypes")
						public void onItemClick(AdapterView parent, View v, int position, long id) {
	                        mOnClickListener.onClick(dialog, position);
	                    }
	                });
	            } 
	            // Attach a given OnItemSelectedListener to the ListView
	            if (mOnItemSelectedListener != null) {
	                listView.setOnItemSelectedListener(mOnItemSelectedListener);
	            }
	            
	            
	            if (mIsSingleChoice) {
	                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	            } else if (mIsMultiChoice) {
	                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	            }
	            
	            if ((listView != null) && (adapter != null)) {
	            	listView.setAdapter(adapter);
	                if (mCheckedItem > -1) {
	                	listView.setItemChecked(mCheckedItem, true);
	                	listView.setSelection(mCheckedItem);
	                }
	            }
	            
		    }
		
		private void createProgress(final CustomDialog dialog,View layout){
			 FrameLayout customPanel = null;
		        if (mView != null) {
		            customPanel = (FrameLayout) layout.findViewById(R.id.customPanel);
		            FrameLayout custom = (FrameLayout) layout.findViewById(R.id.custom);
		            custom.addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams. MATCH_PARENT));
		            layout.findViewById(R.id.contentPanel).setVisibility(View.GONE);
		        } else {
		        	layout.findViewById(R.id.customPanel).setVisibility(View.GONE);
		        }
		}
	}
}
