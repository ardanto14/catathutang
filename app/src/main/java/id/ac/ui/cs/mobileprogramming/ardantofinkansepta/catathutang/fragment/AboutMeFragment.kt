package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.glsurfaceview.MyGLSurfaceView

class AboutMeFragment : Fragment() {

    private lateinit var gLView: GLSurfaceView

    public override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = MyGLSurfaceView(context!!)

        return gLView
    }
}