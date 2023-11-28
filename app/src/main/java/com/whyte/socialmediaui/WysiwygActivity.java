package com.whyte.socialmediaui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.whyte.socialmediaui.databinding.ActivityWysiwygBinding;

import jp.wasabeef.richeditor.RichEditor;

public class WysiwygActivity extends AppCompatActivity {

    private ActivityWysiwygBinding activityWysiwygBinding;
    ActivityResultLauncher<String> imageLauncher;
    ActivityResultLauncher<String> audioLauncher;
    ActivityResultLauncher<String> videoLauncher;
    Dialog EditLinkDialog;
    EditText etEnterLink,etTitle;
    Button btnCancel, btnSave;
    String Html;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWysiwygBinding = ActivityWysiwygBinding.inflate(getLayoutInflater());
        setContentView(activityWysiwygBinding.getRoot());

        //Custom Dialog
        EditLinkDialog = new Dialog(WysiwygActivity.this);
        EditLinkDialog.setContentView(R.layout.dialog_edit);
        EditLinkDialog.setCancelable(false);
        EditLinkDialog.getWindow().setBackgroundDrawable(getApplicationContext().getDrawable(R.drawable.corner_round));
//            CustomDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        etEnterLink = EditLinkDialog.findViewById(R.id.etEnterLink);
        etTitle = EditLinkDialog.findViewById(R.id.etTitle);
        btnCancel = EditLinkDialog.findViewById(R.id.btnCancel);
        btnSave = EditLinkDialog.findViewById(R.id.btnSave);

        activityWysiwygBinding.mEditor.setEditorHeight(200);
        activityWysiwygBinding.mEditor.setEditorFontSize(16);
        activityWysiwygBinding.mEditor.setEditorFontColor(Color.BLACK);
        activityWysiwygBinding.mEditor.setEditorBackgroundColor(Color.WHITE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        activityWysiwygBinding.mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        activityWysiwygBinding.mEditor.setPlaceholder("Insert text here...");
        //mEditor.setInputEnabled(false);
        activityWysiwygBinding.mEditor.getSettings().setAllowFileAccess(true);

        activityWysiwygBinding.mEditor.setHtml(Html);





        activityWysiwygBinding.mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
//                activityWysiwygBinding.mPreview.setText(text);
                Log.e("mPreview",text);
            }
        });

//        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.undo();
//            }
//        });
//
//        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.redo();
//            }
//        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setItalic();
            }
        });

//        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setSubscript();
//            }
//        });
//
//        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setSuperscript();
//            }
//        });

//        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setStrikeThrough();
//            }
//        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setUnderline();
            }
        });

//        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(1);
//            }
//        });
//
//        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(2);
//            }
//        });
//
//        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(3);
//            }
//        });
//
//        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(4);
//            }
//        });
//
//        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(5);
//            }
//        });
//
//        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setHeading(6);
//            }
//        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

//        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
//            private boolean isChanged;
//
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
//                isChanged = !isChanged;
//            }
//        });

//        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setIndent();
//            }
//        });
//
//        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setOutdent();
//            }
//        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setAlignRight();
            }
        });

//        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.setBlockquote();
//            }
//        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityWysiwygBinding.mEditor.insertTodo();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLauncher.launch("image/*");

//                activityWysiwygBinding.mEditor.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg", "dachshund", 320);
            }

        });

        imageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                Log.e("imageLauncher",uri.toString());
                activityWysiwygBinding.mEditor.insertImage(uri.toString(), "dachshund",360);

            }
        });


        findViewById(R.id.action_insert_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioLauncher.launch("audio/*");

//                activityWysiwygBinding.mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3");
            }
        });

        audioLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                Log.e("audioLauncher",uri.toString());
                activityWysiwygBinding.mEditor.insertAudio(uri.toString());

            }
        });

        findViewById(R.id.action_insert_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoLauncher.launch("video/*");

//                activityWysiwygBinding.mEditor.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360);
            }
        });

        videoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                Log.e("videoLauncher",uri.toString());

                activityWysiwygBinding.mEditor.insertVideo(uri.toString(), 360);

            }
        });

//        findViewById(R.id.action_insert_youtube).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityWysiwygBinding.mEditor.insertYoutubeVideo("https://www.youtube.com/watch?v=xcJtL7QggTI");
//            }
//        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditLinkDialog.show();
                etEnterLink.setText("");
                etTitle.setText("");

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditLinkDialog.dismiss();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etEnterLink.getText().toString().isEmpty()){
                            etEnterLink.setError("Please Enter Link.");
                        }else {
                            if (etTitle.getText().toString().isEmpty()){
                                activityWysiwygBinding.mEditor.insertLink(etEnterLink.getText().toString().trim(), etEnterLink.getText().toString().trim());
                                EditLinkDialog.dismiss();
                            }else {
                                activityWysiwygBinding.mEditor.insertLink(etEnterLink.getText().toString().trim(), etTitle.getText().toString().trim());
                                EditLinkDialog.dismiss();
                            }

                        }

                    }
                });
            }
        });


    }
}