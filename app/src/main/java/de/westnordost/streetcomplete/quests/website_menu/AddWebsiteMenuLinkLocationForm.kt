package de.westnordost.streetcomplete.quests.website_menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.databinding.QuestWebsiteLinkBinding
import de.westnordost.streetcomplete.quests.AbstractOsmQuestForm
import de.westnordost.streetcomplete.util.ktx.nonBlankTextOrNull

class AddWebsiteMenuLinkLocationForm : AbstractOsmQuestForm<String>() {

    override val contentLayoutResId = R.layout.quest_website_link
    private val binding by contentViewBinding(QuestWebsiteLinkBinding::bind)

    private val location get() = binding.linkInput.nonBlankTextOrNull

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.linkInput.doAfterTextChanged { checkIsFormComplete() }

        binding.linkInput.doAfterTextChanged {
            if (!Patterns.WEB_URL.matcher(binding.linkInput.getText().toString()).matches()) {
                binding.linkInput.error = getString(R.string.invalid_url_input_warning)
            }
        }
    }

    override fun onClickOk() {
        applyAnswer(location!!)
    }

    override fun isFormComplete() = location != null
}
