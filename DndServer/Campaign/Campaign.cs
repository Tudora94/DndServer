namespace DndServer.Campaign
{
    public class Campaign
    {
        private int campaignID = 1;
        private string campaignName = "Test";
        public int CampaignID { get => campaignID; set => campaignID = value; }
        public string CampaignName { get => campaignName; set => campaignName = value; }
    }
}
