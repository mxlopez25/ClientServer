using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling MVC for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace client_server.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AddressController : ControllerBase
    {
        private readonly IAddressService _addressService;

        public AddressController(IAddressService addressService)
        {
            _addressService = addressService;
        }

        [HttpGet("all")]
        public async Task<IActionResult> All()
        {
            var addresses = await _addressService.All();
            return Ok(addresses);
        }

        [HttpGet("{Id}")]
        public async Task<IActionResult> Get(int Id)
        {
            var address = await _addressService.Get(Id);
            if(address == null)
            {
                return NotFound();
            }
            return Ok(address);
        }

        [HttpPost]
        public async Task<IActionResult> Create([FromBody]Address address)
        {
            var a = await _addressService.Create(address);
            return Ok(a);
        }
    }
}
