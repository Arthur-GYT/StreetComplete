package de.westnordost.streetcomplete.quests.website_menu

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.geometry.ElementGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.Element
import de.westnordost.streetcomplete.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.filter
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.user.achievements.EditTypeAchievement.LIFESAVER
import de.westnordost.streetcomplete.osm.Tags
import de.westnordost.streetcomplete.osm.isPlaceOrDisusedShop

class AddWebsiteMenuLinkLocation : OsmFilterQuestType<String>() {

    override val elementFilter = """
        nodes, ways with
          (
            amenity ~ restaurant|cafe|fast_food|ice_cream|food_court|pub|bar
            or shop = bakery
          )
          and !website:menu
    """
    override val changesetComment = "Specify defibrillator location"
    override val wikiLink = "Tag:emergency=defibrillator"
    override val icon = R.drawable.ic_quest_defibrillator
    override val isDeleteElementEnabled = false
    override val achievements = listOf(LIFESAVER)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_website_menu

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().asSequence().filter { it.isPlaceOrDisusedShop() }

    override fun createForm() = AddWebsiteMenuLinkLocationForm()

    override fun applyAnswerTo(answer: String, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["website:menu"] = answer
    }
}
