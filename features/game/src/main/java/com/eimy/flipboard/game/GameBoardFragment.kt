package com.eimy.flipboard.game

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.eimy.flipboard.base.BaseFragment
import com.eimy.flipboard.contact.R
import com.eimy.flipboard.contact.databinding.GameBoardFragmentBinding
import com.eimy.flipboard.ddsdialog.DDSDialog
import com.eimy.flipboard.game.utils.Constants
import com.feliperrm.utils.getViewModel
import kotlinx.android.synthetic.main.game_board_fragment.*

@RequiresApi(Build.VERSION_CODES.N)
class GameBoardFragment : BaseFragment() {
    private val TAG = "GameBoardFragment"

    private val vm by lazy {
        getViewModel {
            GameViewModel(Constants.GAME_BOARD_ROWS, Constants.GAME_BOARD_COLUMNS)
        }
    }

    private val resetDialog = DDSDialog.newInstance()
        .setImageRes(R.drawable.ic_alert)
        .setTitleRes(R.string.alert_title)
        .setTextRes(R.string.alert_desc)
        .setNegativeButton(R.string.no) { dialog, button ->
            dialog.dismiss()
        }
        .setPositiveButton(R.string.yes) { dialog, button ->
            dialog.dismiss()

            // Reset Board
            vm.initBoard()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = GameBoardFragmentBinding.inflate(inflater, container, false).apply {
        viewModel = vm
        lifecycleOwner = viewLifecycleOwner
        lfo = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerGrid.layoutManager = GridLayoutManager(activity,15)

        vm.showDialog.observe(viewLifecycleOwner, { show ->
            if (show)
                resetDialog.show(childFragmentManager, TAG)
        })

    }
}