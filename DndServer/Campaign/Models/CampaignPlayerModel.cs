namespace DndServer.Campaign.Models
{
    public class CampaignPlayerModel
    {
        private int _id;
        private string _username;
        private string _firstName;
        private string _charachterName;

        public int Id { get => _id; set => _id = value; }
        public string Username { get => _username; set => _username = value; }
        public string FirstName { get => _firstName; set => _firstName = value; }
        public string CharachterName { get => _charachterName; set => _charachterName = value; }
    }
}
