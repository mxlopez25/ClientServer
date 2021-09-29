using System;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace client_server.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpGet("all")]
        public async Task<IActionResult> GetAllUsers()
        {
            var users = await _userService.All();
            return Ok(users);
        }

        [HttpGet("{Id}")]
        public async Task<IActionResult> Get(int Id)
        {
            var user = await _userService.Get(Id);
            if(user == null)
            {
                return NotFound();
            }
            return Ok(user);
        }

        [HttpPost()]
        public async Task<IActionResult> Create([FromBody]User user)
        {
            var u = await _userService.Create(user);
            return Ok(u);
        }
    }
}
