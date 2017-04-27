package qiniu.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import qiniu.auth.JSONObjectRet;
import qiniu.config.Conf;
import qiniu.io.IO;
import qiniu.io.PutExtra;

public class DoUpLoad extends Activity implements View.OnClickListener {
	static final String TAG = DoUpLoad.class.getSimpleName();

	public static final int PICK_PICTURE_RESUMABLE = 0;

	public String UP_TOKEN = Conf.getToken();

	private Button btnUpload;
	private TextView hint;



	// @gist upload
	boolean uploading = false;

	/**
	 * 普通上传文件
	 * 
	 * @param uri
	 */
	private void doUpload(Uri uri) {
		if (uploading) {
			hint.setText("上传中，请稍后");
			return;
		}
		uploading = true;
		String key = Conf.getUploadFileName();
		PutExtra extra = new PutExtra();
		extra.checkCrc = PutExtra.AUTO_CRC32;
		extra.params.put("x:from", "android");
		hint.setText("上传中");
		IO.putFile(this, UP_TOKEN, key, uri, extra, new JSONObjectRet() {
            @Override
            public void onSuccess(JSONObject resp) {
                Log.e(TAG, resp.toString());
                uploading = false;
                String hash;
                try {
                    hash = resp.getString("hash");
                } catch (Exception ex) {
                    hint.setText(ex.getMessage());
                    return;
                }

                hint.setText("上传成功! " + hash);
//				 Intent intent = new Intent(Intent.ACTION_VIEW,
//				 Uri.parse(redirect));
                // startActivity(intent);
            }

            @Override
            public void onFailure(Exception ex) {
                uploading = false;
                hint.setText("错误: " + ex.getMessage());
            }
        });
	}

	// @endgist

	@Override
	public void onClick(View view) {
		if (view.equals(btnUpload)) {
			Intent i = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, PICK_PICTURE_RESUMABLE);
			return;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		if (requestCode == PICK_PICTURE_RESUMABLE) {
			doUpload(data.getData());
			return;
		}
	}
}
