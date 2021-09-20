package com.jimmy.dongdaedaek.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jimmy.dongdaedaek.FontLicenseActivity
import com.jimmy.dongdaedaek.databinding.FragmentMyPageBinding
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import org.koin.android.scope.ScopeFragment

class MyPageFragment : ScopeFragment(), MyPageContract.View {
    override val presenter: MyPageContract.Presenter by inject()
    var binding: FragmentMyPageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyPageBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        presenter.onViewCreated()
    }

    private fun bindView() {
        binding?.loginOutButton?.setOnClickListener {
            if (presenter.loggedIn) {
                presenter.logout()
            } else {
                val email = binding?.emailEditText?.text.toString()
                presenter.requestLogin(email)
                binding?.emailEditText?.text?.clear()
            }
        }

        binding?.emailEditText?.addTextChangedListener { editable ->
            binding?.loginOutButton?.isEnabled = editable?.length ?: 0 > 0
        }

        binding?.wishStoreListButton?.setOnClickListener {
            findNavController().navigate(MyPageFragmentDirections.toWishList())
        }

        binding?.ossLicenses?.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
        }
        binding?.fontLicense?.setOnClickListener {
            startActivity(Intent(requireContext(),FontLicenseActivity::class.java))
        }



    }

    override fun showUserInfo(email: String) {
        binding?.emailEditText?.isEnabled = false
        binding?.emailEditText?.setText(email)
        binding?.loginOutButton?.text = "로그아웃"
    }

    override fun showLoginPage() {
        binding?.emailEditText?.editableText?.clear()
        binding?.emailEditText?.isEnabled = true
        binding?.loginOutButton?.isEnabled = true
        binding?.loginOutButton?.text = "로그인"
    }


    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun showProgressBar() {
        binding?.progressView?.toVisible()
    }

    override fun hideProgressBar() {
        binding?.progressView?.toGone()
    }

    override fun showErrorToast() {
        Toast.makeText(context, "예상치 못한 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun emailSentToast() {
        Toast.makeText(context, "인증메일이 발송되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun logoutSuccessToast() {
        Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
    }
}


