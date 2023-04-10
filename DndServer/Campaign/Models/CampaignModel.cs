namespace DndServer.Campaign.Models
{
    public class CampaignModel
    {
        private int campaignID;
        private string campaignName;
        private SourcesModel sources = new SourcesModel();
        private bool advancementType;
        private bool hpType;
        private bool weightType;
        private bool goldWeight;
        //private List<int> allowedRaces;
        public int CampaignID { get => campaignID; set => campaignID = value; }
        public string CampaignName { get => campaignName; set => campaignName = value; }
        public SourcesModel Sources { get => sources; set => sources = value; }
        public bool AdvancementType { get => advancementType; set => advancementType = value; }
        public bool HpType { get => hpType; set => hpType = value; }
        public bool WeightType { get => weightType; set => weightType = value; }
        public bool GoldWeight { get => goldWeight; set => goldWeight = value; }
        //public List<int> AllowedRaces { get => allowedRaces; set => allowedRaces = value; }
    }
}
