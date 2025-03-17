package com.tudorEnterprises.dndapp.constants

enum class Screen(val route: String) {
    Home("homeScreen"),
    CreateUser("create_user"),
    DmOrPlayer("Dm_Or_Player"),
    DMLanding("DM_Landing"),
    PlayerLanding("Player_Landing"),
    Campaign("Dm_Campaign")
}

enum class Buttons(val buttonText: String, val buttonRoute: Screen) {
    CreateNewUser("Register", Screen.CreateUser),
    DungeonMaster("Dungeon Master", Screen.DMLanding),
    Player("Player", Screen.PlayerLanding),
    NewCampaign("Add New Campaign", Screen.DMLanding),
    DmTools("DM Tools", Screen.DMLanding)
}