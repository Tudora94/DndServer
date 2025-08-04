package com.tudorEnterprises.dndapp.constants

@Suppress("unused")
enum class Screen(val route: String) {
    Home("homeScreen"),
    CreateUser("create_user"),
    DmOrPlayer("Dm_Or_Player"),
    DMLanding("DM_Landing"),
    PlayerLanding("Player_Landing"),
    Campaign("Dm_Campaign"),
    Character("Player_Character"),
    CampaignInventory("Campaign_Inventory"),
    InventoryItem("Inventory_Item"),
}
@Suppress("unused")
enum class Buttons(val buttonText: String, val buttonRoute: Screen) {
    CreateNewUser("Register", Screen.CreateUser),
    DungeonMaster("Dungeon Master", Screen.DMLanding),
    Player("Player", Screen.PlayerLanding),
//    NewCampaign("Add New Campaign", Screen.DMLanding),
//    DmTools("DM Tools", Screen.DMLanding)
}

@Suppress("unused")
enum class UserRole(val role: String) {
    DUNGEON_MASTER("Dungeon Master"),
    PLAYER("Player"),
}