namespace DndServer.Campaign
{
    public class Campaign
    {
        private int campaignID;
        private string campaignName;
        private int numberOfPlayers;
        private CampaignPreferences preferences = new CampaignPreferences();
        public int CampaignID { get => campaignID; set => campaignID = value; }
        public string CampaignName { get => campaignName; set => campaignName = value; }
        public int NumberOfPlayers { get => numberOfPlayers; set => numberOfPlayers = value; }
        public CampaignPreferences Preferences { get => preferences; set => preferences = value; }
    }
}
