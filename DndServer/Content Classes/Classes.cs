namespace DndServer.Content_Classes
{
    public class ClassNames
    {
        // ints and list strings will relate to Db inforamtion (ints connects to classID spells lists etc) 
        private List<string> className;
        private int classSourceID;
        private int classID;
        private string classDescr;
        private int hitPoints;
        private int level;
        private List<int> spells;
        private List<int> abilities;
        private List<int> classProficiencies;
        private List<int> defence;

        public List<string> ClassName { get => className; set => className = value; }
        public int ClassSourceID { get => classSourceID; set => classSourceID = value; }
        public int ClassID { get => classID; set => classID = value; }
        public string ClassDescr { get => classDescr; set => classDescr = value; }
        public int HitPoints { get => hitPoints; set => hitPoints = value; }
        public int Level { get => level; set => level = value; }
        public List<int> Spells1 { get => Spells; set => Spells = value; }
        public List<int> Abilities { get => abilities; set => abilities = value; }
        public List<int> ClassProficiencies { get => classProficiencies; set => classProficiencies = value; }
        public List<int> Defence { get => defence; set => defence = value; }
    }
}
