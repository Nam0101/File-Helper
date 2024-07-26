package nv.nam.filehelper

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    private val btn: Button by lazy { findViewById(R.id.button) }
    private val authBtn by lazy { findViewById<Button>(R.id.auth) }
    private val imv by lazy { findViewById<ImageView>(R.id.imageView) }
    //inject by koin
    private val mainViewModel: MainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        requestStoragePermission(this, {
            fetchFiles()
        }, {
            Log.i("Permission", "Permission denied")
        })
        btn.setOnClickListener{
            fetchFiles()
        }
//        //glide
//       Glide.with(this)
//            .load("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
//            .into(imv)
    }

    private fun requestStoragePermission(
        context: Context, onGranted: () -> Unit, onDenied: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                onGranted()
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:${context.packageName}")
                (context as? AppCompatActivity)?.startActivityForResult(
                    intent, REQUEST_CODE_STORAGE_PERMISSION
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as AppCompatActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                onGranted()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 1001
    }

    private fun fetchFiles() {
        mainViewModel.getAllFiles(0, 10)
        mainViewModel.files.observe(this) { file ->
            file.forEach {
                Log.i("File", it.name)
            }
        }
    }
}