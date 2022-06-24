const pokeBtn = document.getElementById("poke-btn");
const pokeIdInput = document.getElementById("poke-id");
const pokeContainer = document.getElementById("poke-container");

function pokeFetch(e){
    e.preventDefault();
    console.log("Fetching...")
    const id = pokeIdInput.value;
    console.log(id);
    if(id <= 0 || id > 898) return;
    fetch(`https://pokeapi.co/api/v2/pokemon-species/${id}`)
        .then(res => res.json())
        .then(renderMon)
        .catch(e => console.error(e));
}

function renderMon(pokemon){
    console.log(pokemon);
    let desc = pokemon.flavor_text_entries.filter((d) => d.language.name === "en")
    pokeContainer.innerHTML = `
        <h3 class="card-title">#${pokemon.id}, ${pokemon.name.charAt(0).toUpperCase() + pokemon.name.slice(1)}</h3>
        <img class="mx-auto d-block" width=96 height=96 src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png" alt="The pokemon ${pokemon.name}" />
        <p class="card-text">${desc[0].flavor_text.replace("", " ")}</p>
    `;
    pokeContainer.style.display = "block";
}

pokeBtn.addEventListener("click", pokeFetch);