namespace DndServer.Campaign
{
    public class Campaign
    {
        private int campaignID = 1;
        private string campaignName = "Test";
        private int players;
        private string campaignPreferences;
        private string advancementType;
        private string hpType;
        private string weightType;

        public int CampaignID { get => campaignID; set => campaignID = value; }
        public string CampaignName { get => campaignName; set => campaignName = value; }

    }
}
