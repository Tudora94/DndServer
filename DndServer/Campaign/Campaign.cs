namespace DndServer.Campaign
{
    public class Campaign
    {
        private int campaignID;
        private string campaignName;
        private int numberOfPlayers;
        private CampaignPreferences dMSettings;

        public int CampaignID { get => campaignID; set => campaignID = value; }
        public string CampaignName { get => campaignName; set => campaignName = value; }
        public int NumberOfPlayers { get => numberOfPlayers; set => numberOfPlayers = value; }
        public CampaignPreferences DMSettings { get => dMSettings; set => dMSettings = value; }

        public void generateCampaignPreferences () 
        {
            DMSettings = new CampaignPreferences();
        }
    }
}
