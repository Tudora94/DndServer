namespace DndServer.Campaign
{ 
	public class Race
	{
        private int raceID;
        private int sourceID;
        private string raceName;
		private string raceDescr;
		//private raceStats; (create class)
		//private raceTraits;(create class)
        //private raceProficencies; (Create class) 
		private List<string> raceLagnuages;
		private int raceSpeed;

        public int RaceID { get => raceID; set => raceID = value; }
        public int SourceID { get => sourceID; set => sourceID = value; }
        public string RaceName { get => raceName; set => raceName = value; }
        public string RaceDescr { get => raceDescr; set => raceDescr = value; }
        public List<string> RaceLagnuages { get => raceLagnuages; set => raceLagnuages = value; }
        public int RaceSpeed { get => raceSpeed; set => raceSpeed = value; }

    }
}
