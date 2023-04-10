using System.Net;

namespace DndServer.Campaign.Models
{
    public class Response
    {
        private string responseString;
        private HttpStatusCode statusCode;
        public string ResponseString { get => responseString; set => responseString = value; }
        public HttpStatusCode StatusCode { get => statusCode; set => statusCode = value; }
    }
}
