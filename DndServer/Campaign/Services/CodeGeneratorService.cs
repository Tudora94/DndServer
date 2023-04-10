namespace DndServer.Campaign.Services
{
    public class CodeGeneratorService
    {
        private string? generatedCode;

        public string GeneratedCode { get => generatedCode; set => generatedCode = value; }

        public void generator()
        {
            List<string> RandomIntegers = new List<string>();
            Random rnd = new Random();

            for (int i = 0; i < 6; i++)
            {
                string c = Convert.ToString(rnd.Next(9));
                RandomIntegers.Add(c);
            }

            foreach(string s in RandomIntegers)
            {
                generatedCode += s;
            }
        }
    }
}
