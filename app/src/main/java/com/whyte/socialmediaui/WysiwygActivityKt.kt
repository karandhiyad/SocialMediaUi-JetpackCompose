package com.whyte.socialmediaui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.whyte.socialmediaui.databinding.ActivityWysiwygKtBinding
import jp.wasabeef.richeditor.RichEditor.OnTextChangeListener

class WysiwygActivityKt : AppCompatActivity() {

    private lateinit var activityWysiwygKtBinding: ActivityWysiwygKtBinding
    private lateinit var imageLauncher: ActivityResultLauncher<String>
    private lateinit var audioLauncher: ActivityResultLauncher<String>
    private lateinit var videoLauncher: ActivityResultLauncher<String>
    private lateinit var editLinkDialog: Dialog
    private lateinit var etEnterLink: EditText
    private lateinit var etTitle: EditText
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var htmlString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWysiwygKtBinding = ActivityWysiwygKtBinding.inflate(layoutInflater)
        setContentView(activityWysiwygKtBinding.root)

        //Custom Dialog
        editLinkDialog = Dialog(this@WysiwygActivityKt)
        editLinkDialog.setContentView(R.layout.dialog_edit)
        editLinkDialog.setCancelable(false)
        editLinkDialog.window!!.setBackgroundDrawable(applicationContext.getDrawable(R.drawable.corner_round))
//            CustomDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        etEnterLink = editLinkDialog.findViewById(R.id.etEnterLink)
        etTitle = editLinkDialog.findViewById(R.id.etTitle)
        btnCancel = editLinkDialog.findViewById(R.id.btnCancel)
        btnSave = editLinkDialog.findViewById(R.id.btnSave)

        activityWysiwygKtBinding.mEditor.setEditorHeight(200)
        activityWysiwygKtBinding.mEditor.setEditorFontSize(16)
        activityWysiwygKtBinding.mEditor.setEditorFontColor(Color.BLACK)
        activityWysiwygKtBinding.mEditor.setEditorBackgroundColor(Color.WHITE)
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        activityWysiwygKtBinding.mEditor.setPadding(10, 10, 10, 10)
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        activityWysiwygKtBinding.mEditor.setPlaceholder("Insert text here...")
        //mEditor.setInputEnabled(false);
        //mEditor.setInputEnabled(false);
        activityWysiwygKtBinding.mEditor.settings.allowFileAccess = true
//        htmlString = "Hello this is demo for html.<br><b>Demo&nbsp;</b><img src=\"content://com.android.providers.media.documents/document/image%3A277274\" alt=\"dachshund\" width=\"360\">"
//        activityWysiwygKtBinding.mEditor.html = htmlString



        activityWysiwygKtBinding.mEditor.setOnTextChangeListener(OnTextChangeListener { text ->
            Log.e("mPreview", text)
        })

//        findViewById<View>(R.id.action_undo).setOnClickListener { activityWysiwygKtBinding.mEditor.undo() }
//
//        findViewById<View>(R.id.action_redo).setOnClickListener { activityWysiwygKtBinding.mEditor.redo() }

        activityWysiwygKtBinding.actionBold.setOnClickListener { activityWysiwygKtBinding.mEditor.setBold() }

        activityWysiwygKtBinding.actionItalic.setOnClickListener { activityWysiwygKtBinding.mEditor.setItalic() }

//        findViewById<View>(R.id.action_subscript).setOnClickListener { activityWysiwygKtBinding.mEditor.setSubscript() }
//
//        findViewById<View>(R.id.action_superscript).setOnClickListener { activityWysiwygKtBinding.mEditor.setSuperscript() }

//        findViewById<View>(R.id.action_strikethrough).setOnClickListener { activityWysiwygKtBinding.mEditor.setStrikeThrough() }

        activityWysiwygKtBinding.actionUnderline.setOnClickListener { activityWysiwygKtBinding.mEditor.setUnderline() }

//        findViewById<View>(R.id.action_heading1).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(1) }
//
//        findViewById<View>(R.id.action_heading2).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(2) }
//
//        findViewById<View>(R.id.action_heading3).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(3) }
//
//        findViewById<View>(R.id.action_heading4).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(4) }
//
//        findViewById<View>(R.id.action_heading5).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(5) }
//
//        findViewById<View>(R.id.action_heading6).setOnClickListener { activityWysiwygKtBinding.mEditor.setHeading(6) }

        activityWysiwygKtBinding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                activityWysiwygKtBinding.mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

//        findViewById<View>(R.id.action_bg_color).setOnClickListener(object : View.OnClickListener {
//            private var isChanged = false
//            override fun onClick(v: View) {
//                activityWysiwygKtBinding.mEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
//                isChanged = !isChanged
//            }
//        })

//        findViewById<View>(R.id.action_indent).setOnClickListener { activityWysiwygKtBinding.mEditor.setIndent() }
//
//        findViewById<View>(R.id.action_outdent).setOnClickListener { activityWysiwygKtBinding.mEditor.setOutdent() }

        activityWysiwygKtBinding.actionAlignLeft.setOnClickListener { activityWysiwygKtBinding.mEditor.setAlignLeft() }

        activityWysiwygKtBinding.actionAlignCenter.setOnClickListener { activityWysiwygKtBinding.mEditor.setAlignCenter() }

        activityWysiwygKtBinding.actionAlignRight.setOnClickListener { activityWysiwygKtBinding.mEditor.setAlignRight() }

//        findViewById<View>(R.id.action_blockquote).setOnClickListener { activityWysiwygKtBinding.mEditor.setBlockquote() }

        activityWysiwygKtBinding.actionInsertBullets.setOnClickListener { activityWysiwygKtBinding.mEditor.setBullets() }

        activityWysiwygKtBinding.actionInsertNumbers.setOnClickListener { activityWysiwygKtBinding.mEditor.setNumbers() }

        activityWysiwygKtBinding.actionInsertCheckbox.setOnClickListener { activityWysiwygKtBinding.mEditor.insertTodo() }

        activityWysiwygKtBinding.actionInsertImage.setOnClickListener {
            imageLauncher.launch("image/*")
//            activityWysiwygKtBinding.mEditor.insertImage(
//                "https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
//                "dachshund",
//                320
//            )
        }

        imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.e("imageLauncher", it.toString())
            activityWysiwygKtBinding.mEditor.insertImage(it.toString(), "dachshund", 360)
        }

        activityWysiwygKtBinding.actionInsertAudio.setOnClickListener {
            audioLauncher.launch("audio/*")
//            activityWysiwygKtBinding.mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3")
        }

        audioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.e("audioLauncher", it.toString())
            activityWysiwygKtBinding.mEditor.insertAudio(it.toString())
        }

        activityWysiwygKtBinding.actionInsertVideo.setOnClickListener {
            videoLauncher.launch("video/*")

//            activityWysiwygKtBinding.mEditor.
//        insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360)
        }

        videoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.e("videoLauncher", it.toString())
            activityWysiwygKtBinding.mEditor.insertVideo(it.toString(), 360)
        }
//        findViewById<View>(R.id.action_insert_youtube).setOnClickListener { activityWysiwygKtBinding.mEditor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA") }

        activityWysiwygKtBinding.actionInsertLink.setOnClickListener {
//            activityWysiwygKtBinding.mEditor.insertLink(
//                "https://github.com/wasabeef",
//                "wasabeef"
//            )

            editLinkDialog.show()
            etEnterLink.setText("")
            etTitle.setText("")

            btnCancel.setOnClickListener { editLinkDialog.dismiss() }

            btnSave.setOnClickListener {
                if (etEnterLink.text.toString().isEmpty()) {
                    etEnterLink.error = "Please Enter Link."
                } else {
                    if (etTitle.text.toString().isEmpty()) {
                        activityWysiwygKtBinding.mEditor.insertLink(
                            etEnterLink.text.toString().trim { it <= ' ' },
                            etEnterLink.text.toString().trim { it <= ' ' })
                        editLinkDialog.dismiss()
                    } else {
                        activityWysiwygKtBinding.mEditor.insertLink(
                            etEnterLink.text.toString().trim { it <= ' ' },
                            etTitle.text.toString().trim { it <= ' ' })
                        editLinkDialog.dismiss()
                    }
                }
            }
        }

    }
}