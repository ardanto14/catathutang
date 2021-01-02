package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R

class DeniedPermissionFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ) : View {

        val mView: View = inflater.inflate(R.layout.denied_permission, container, false)

        return mView

    }


}
