package com.felipersn.idwallproject.presentation.ui.mainlist

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.common.extension.isNetworkAvailable
import com.felipersn.idwallproject.common.extension.longToast
import com.felipersn.idwallproject.common.extension.toast
import com.felipersn.idwallproject.common.tools.Resource
import com.felipersn.idwallproject.presentation.base.BaseActivity
import com.felipersn.idwallproject.presentation.ui.imagepreview.ImagePreviewActivity
import com.felipersn.idwallproject.presentation.ui.login.LoginActivity
import com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category.MainListCategoryAdapter
import com.felipersn.idwallproject.presentation.ui.mainlist.adapter.category.MainListCategoryAdapterListener
import com.felipersn.idwallproject.presentation.ui.mainlist.adapter.feed.MainLisFeedAdapter
import com.felipersn.idwallproject.presentation.ui.mainlist.adapter.feed.MainListFeedAdapterListener
import kotlinx.android.synthetic.main.activity_main_list.*
import kotlinx.android.synthetic.main.include_main_list_filter.*
import javax.inject.Inject


class MainListActivity : BaseActivity(), MainListCategoryAdapterListener, MainListFeedAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainListViewModel: MainListViewModel

    private lateinit var mainListCategoryAdapter: MainListCategoryAdapter
    private lateinit var mainListFeedAdapter: MainLisFeedAdapter

    private val animatorSet = AnimatorSet()
    private var height = 0
    private var backdropShown = false

    private val filterToCloseAVD: AnimatedVectorDrawable by lazy {
        (ContextCompat.getDrawable(baseContext, R.drawable.avd_filter_to_close)) as AnimatedVectorDrawable
    }

    private val closeToFilterAVD: AnimatedVectorDrawable by lazy {
        (ContextCompat.getDrawable(baseContext, R.drawable.avd_close_to_filter)) as AnimatedVectorDrawable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        initView()
    }

    private fun initView() {
        setupViewModel()
        setupRecyclerViews()
        setupListeners()
        setupObservers()
        setupViewHeight()

        populateCategories()
        startApiConnection()
    }

    private fun setupViewModel() {
        mainListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainListViewModel::class.java)
    }

    private fun setupListeners() {
        buttonFilter.setOnClickListener {
            animateBackdropFilter()
        }

        swipeRefreshFeed.setOnRefreshListener {
            mainListViewModel.getFeed()
        }

        buttonLogout.setOnClickListener {
            mainListViewModel.invalidateLoginToken()
            startActivity(LoginActivity.provideIntent(baseContext))
            finishAfterTransition()
        }
    }

    private fun setupObservers() {
        mainListViewModel.feedLiveData.observe(this, Observer { it ->
            it?.getContentIfNotHandled().let { resource ->
                resource?.let { result ->
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            mainListFeedAdapter.setMessageList(result.data!!)
                            toggleSwipeRefresh(false)
                        }
                        Resource.Status.LOADING -> {
                            toggleSwipeRefresh(true)
                        }
                        Resource.Status.ERROR -> {
                            toggleSwipeRefresh(false)
                            if (isNetworkAvailable()) {
                                result.error?.let { message ->
                                    toast(message)
                                }
                            } else {
                                longToast(getString(R.string.offline))
                            }
                        }
                    }
                }
            }
        })
    }

    private fun toggleSwipeRefresh(enable: Boolean) {
        when (enable) {
            true -> {
                if (!swipeRefreshFeed.isRefreshing) swipeRefreshFeed.isRefreshing = true
            }
            false -> {
                if (swipeRefreshFeed.isRefreshing) swipeRefreshFeed.isRefreshing = false
            }
        }
    }

    private fun setupRecyclerViews() {
        mainListCategoryAdapter = MainListCategoryAdapter(this)
        mainListFeedAdapter = MainLisFeedAdapter(this)

        recyclerViewCategory.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        recyclerViewCategory.adapter = mainListCategoryAdapter


        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.invalidateSpanAssignments()
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerViewFeed.layoutManager = staggeredGridLayoutManager
        recyclerViewFeed.itemAnimator = DefaultItemAnimator()
        recyclerViewFeed.adapter = mainListFeedAdapter
    }

    private fun startApiConnection() {
        mainListViewModel.getFeed()
    }

    private fun populateCategories() {
        mainListCategoryAdapter.setMessageList(mainListViewModel.categoryList)

        updateCategoryInView(mainListViewModel.categoryList[0], false)
    }

    private fun updateCategoryInView(category: String, updateBackdrop: Boolean) {
        mainListViewModel.category = category
        textViewCategory.text = String.format(getString(R.string.mainListActivity_categoryLabel), category)
        if (updateBackdrop) {
            recyclerViewFeed.scrollToPosition(0)
            animateBackdropFilter()
            mainListViewModel.getFeed()
        }
    }

    private fun animateButtonIcon() {
        if (backdropShown) {
            buttonFilter.setImageDrawable(filterToCloseAVD)
            filterToCloseAVD.start()
        } else {
            buttonFilter.setImageDrawable(closeToFilterAVD)
            closeToFilterAVD.start()
        }
    }

    private fun animateBackdropFilter() {
        backdropShown = !backdropShown

        animateButtonIcon()

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = height - 200

        val animator = ObjectAnimator.ofFloat(
            swipeRefreshFeed,
            "translationY",
            (if (backdropShown) translateY else 0).toFloat()
        )
        animator.duration = 400
        animator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(animator)
        animator.start()
    }

    private fun setupViewHeight() {
        swipeRefreshFeed.post {
            height = swipeRefreshFeed.height
        }
    }

    override fun onCategoryClick(category: String) {
        updateCategoryInView(category, true)
    }

    override fun onItemClick(url: String, imageView: ImageView) {
        val intent = ImagePreviewActivity.provideIntent(baseContext, url)

        val activityOptinsCompatImagePreview = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            url
        )

        startActivity(intent, activityOptinsCompatImagePreview.toBundle())
    }

    companion object {

        fun provideIntent(context: Context): Intent {
            return Intent(context, MainListActivity::class.java)
        }
    }
}
