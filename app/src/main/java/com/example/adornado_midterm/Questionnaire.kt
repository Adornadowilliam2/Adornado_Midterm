package com.example.adornado_midterm


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.adornado_midterm.databinding.FragmentQuestionnaireBinding

class Questionnaire : Fragment() {

    private lateinit var binding: FragmentQuestionnaireBinding

    private val viewmodel: MainViewModel by activityViewModels()

    private var groups: MutableList<Group> =
        mutableListOf(
            Group(
                "What is the fruit Galilee hit to know the gravity?",
                listOf("Meteor","Apple","Bomb","Einstein's Brain")
            ),
            Group(
                "Who died in MCU?",
                listOf("Superman","IronMan","Kobe Bryan", "Tecnoblade")
            ),
            Group(
                "Where is the fullname of Zorro?",
                listOf("Ronoroa Zorro","Don Diego de la Vega","Mojo Jojo","Ronoroa Zoro")
            ),
            Group(
                "Who killed the God Olympus?",
                listOf("Jörmungandr","Kratos","Ed Caluag","Cardo Dalisay")
            ),
            Group(
                "Who is the first pokemon ever made, and coded into the game",
                listOf("Egg","Rhydon","Arceus","Bulbasaur")
            ),
            Group(
                "?sisoinoconaclovociliscipocsorcimartluonomuenp si tahW",
                listOf("Inhaling Ash and Pikachu","Inhaling ash and sand dust.","fear of needles","Hotdog")
            ),
            Group(
                "What is the name of mother of Jin in Tekken?",
                listOf("Kazumi Mishima","Jun Kazama","Chizuru Kagura","Juri Han")
            ),
            Group(
                "What is API?",
                listOf("American Petroleum Institute","Application Programming Interface","Android Programming Institute","Apk Playable Idk")
            ),
            Group(
                "What is the total of 11 times 11?",
                listOf("Huwag toh Juan","121","111","1111")
            ),
            Group(
                "Who is the Windows 12 brand ambassador?",
                listOf("Pavitr Prabhakar","Dolly Chaiwala","Alden Recharge","Bill Gates")
            )
        )
    private var members: MutableList<String> = mutableListOf()
    private var selectedMember: String = ""
    private var questionString: String = ""
    //currentIndexGroup will be incremented
    private var currentIndexGroup = 0
    private var score = 0
    //to know if we are at the last group
    private val max_group = groups.count() - 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionnaireBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: something
        if (savedInstanceState == null) {
            //shuffle group
            viewmodel.setAllGroups(groups)
            //preserve the shuffled groups
            viewmodel.setAllCopyGroups(groups)
        }
        viewmodel.groups.observe(viewLifecycleOwner){grp: MutableList<Group> ->
            if(savedInstanceState == null){
                //set members var to group's members in viewmodel
                //to make it into a mutablelist do .toMutableList()
                members = grp[currentIndexGroup].groupMembers.toMutableList()
                //shuffle members
                members.shuffle()

            }
            else{
                currentIndexGroup = savedInstanceState.getInt("cquestion_key")
                questionString = savedInstanceState.getString("cquestion_string").toString()
                members = savedInstanceState.getStringArrayList("members")?.toMutableList()!!
                selectedMember = savedInstanceState.getString("selected_mem").toString()
                score = savedInstanceState.getInt("score_key")
                binding.tvResult.text = selectedMember
                binding.quesNum.text = questionString
            }
            //apply to layout
            binding.tvGroupName.text = grp[currentIndexGroup].groupName
            binding.rbItem1.text = members[0]
            binding.rbItem2.text = members[1]
            binding.rbItem3.text = members[2]
            binding.rbItem4.text = members[3]

            //on orientation change, buttons and some objects needs to stay hidden
            if (currentIndexGroup >= max_group){
                binding.btnGet.visibility = View.GONE
                binding.rgItems.visibility = View.GONE
                binding.tvGroupName.visibility = View.GONE
            }

            //button events can be placed inside an observer
            binding.btnGet.setOnClickListener { view: View ->
                val checkedItem = binding.rgItems.checkedRadioButtonId

                //check if no items were selected
                //no chosen radio button returns -1
                if(checkedItem != -1){
                    //shortcut code for choosing an item
                    //when case can be assigned to a variable
                    val selectedItem = when(checkedItem){
                        R.id.rbItem1 -> 0
                        R.id.rbItem2 -> 1
                        R.id.rbItem3-> 2
                        R.id.rbItem4 -> 3
                        else -> -1
                    }



                    viewmodel.copyGroups.observe(viewLifecycleOwner){
                            cpy: MutableList<Group> ->
                        //on button click
                        selectedMember = members[selectedItem]
                        binding.tvResult.text = selectedMember
                        if(currentIndexGroup == max_group){
                            //to another fragment
                            binding.btnGet.visibility = View.GONE
                            binding.rgItems.visibility = View.GONE
                            binding.tvGroupName.visibility = View.GONE
                        }else{
                            //let's say the star member is index position 1
                            if(members[selectedItem] == cpy[currentIndexGroup].groupMembers[1]){
                                binding.rgItems.clearCheck()
                                currentIndexGroup++

                                //layout
                                binding.tvGroupName.text = grp[currentIndexGroup].groupName
                                //shuffle members
                                members = grp[currentIndexGroup].groupMembers.toMutableList()
                                //shuffle members
                                members.shuffle()

                                //apply to rbitems
                                binding.rbItem1.text = members[0]
                                binding.rbItem2.text = members[1]
                                binding.rbItem3.text = members[2]
                                binding.rbItem4.text = members[3]
                            } else{
                                // still get the answer even if it is wrong
                                selectedMember = members[selectedItem]
                                binding.tvResult.text = selectedMember
                                binding.rgItems.clearCheck()
                                currentIndexGroup++

                                // layout
                                binding.tvGroupName.text = grp[currentIndexGroup].groupName
                                // shuffle members
                                members = grp[currentIndexGroup].groupMembers.toMutableList()
                                // shuffle members
                                members.shuffle()

                                // apply to rbitems
                                binding.rbItem1.text = members[0]
                                binding.rbItem2.text = members[1]
                                binding.rbItem3.text = members[2]
                                binding.rbItem4.text = members[3]
                            }
                        }

                    }
                }

            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        // current group
        outState.putInt("cquestion_key",currentIndexGroup)
        outState.putString("cquestion_string", questionString)
        //Answer
        outState.putStringArrayList("members",ArrayList(members))
        //Answer type
        outState.putString("selected_mem", selectedMember)
        outState.putInt("score_key", score)
        super.onSaveInstanceState(outState)
    }

}