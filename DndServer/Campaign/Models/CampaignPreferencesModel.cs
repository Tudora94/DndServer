namespace DndServer.Campaign.Models
{
    public class CampaignPreferencesModel
    {
        private List<String> sources;
        private bool advancementType;
        private bool hpType;
        private bool weightType;
        private bool goldWeight;
        private List<int> allowedRaces;

        public List<string> Sources { get => sources; set => sources = value; }
        public bool AdvancementType { get => advancementType; set => advancementType = value; }
        public bool HpType { get => hpType; set => hpType = value; }
        public bool WeightType { get => weightType; set => weightType = value; }
        public bool GoldWeight { get => goldWeight; set => goldWeight = value; }
        public List<int> AllowedRaces { get => allowedRaces; set => allowedRaces = value; }
    }
}
