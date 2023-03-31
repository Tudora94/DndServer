namespace DndServer.Character
{
    public class Character
    {
        private string _name;
        private CharacterRaceOptions raceOptions = new CharacterRaceOptions();
        private string _characterRace;

        public string Name { get => _name; set => _name = value; }
        public string CharacterRace { get => _characterRace; set => _characterRace = value; }
    }
}
