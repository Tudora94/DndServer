using System;
using System.Collections.Generic;

namespace DndServer.Campaign
{
    public class CampaignPreferences
    {
        private List<int> sources;
        private static bool advancementType;
        private bool hpType;
        private bool weightType;
        private bool goldWieght;

        public List<int> Sources { get => sources; set => sources = value; }
        public bool AdvancementType { get => advancementType; set => advancementType = value; }
        public bool HpType { get => hpType; set => hpType = value; }
        public bool WeightType { get => weightType; set => weightType = value; }
        public bool GoldWieght { get => goldWieght; set => goldWieght = value; }
    }
}


