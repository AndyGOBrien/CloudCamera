package com.llamalabb.cloudcamera.main.gallery


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.llamalabb.cloudcamera.R
import kotlinx.android.synthetic.main.fragment_gallery_tabs.view.*

/**
 * Created by andy on 12/18/17.
 */
class GalleryTabFragment : Fragment() {

    private var page: Int? = null
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", 0)
        title = arguments.getString("title")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_gallery_tabs, container, false)
        val viewPager = view.gallery_view_pager
        val tabLayout = view.gallery_tab_layout
        viewPager.adapter = MyPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    companion object {
        fun newInstance(pageNum: Int, title: String) : GalleryTabFragment {
            val galFrag = GalleryTabFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            galFrag.arguments = args
            return galFrag
        }
    }
}