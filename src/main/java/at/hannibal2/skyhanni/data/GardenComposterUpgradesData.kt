package at.hannibal2.skyhanni.data

import at.hannibal2.skyhanni.data.model.ComposterUpgrade
import at.hannibal2.skyhanni.events.InventoryFullyOpenedEvent
import at.hannibal2.skyhanni.features.garden.GardenAPI
import at.hannibal2.skyhanni.features.garden.composter.ComposterAPI
import at.hannibal2.skyhanni.utils.ItemUtils.name
import at.hannibal2.skyhanni.utils.NumberUtil.romanToDecimalIfNecessary
import at.hannibal2.skyhanni.utils.RegexUtils.matchMatcher
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class GardenComposterUpgradesData {

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryFullyOpenedEvent) {
        if (!GardenAPI.inGarden()) return
        if (event.inventoryName != "Composter Upgrades") return
        for (item in event.inventoryItems.values) {
            ComposterUpgrade.regex.matchMatcher(item.name) {
                val name = group("name")
                val level = group("level")?.romanToDecimalIfNecessary() ?: 0
                val composterUpgrade = ComposterUpgrade.getByName(name)!!
                ComposterAPI.composterUpgrades?.put(composterUpgrade, level)
            }
        }
    }
}
