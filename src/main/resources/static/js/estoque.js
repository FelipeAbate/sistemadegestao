
//SCRIPT PARA ESTOQUE


//Função para preencher form Cadastro de Produtos (POST / INSERT INTO calcas...)
async function submitForm() {
    const form = document.getElementById('productForm');
    const formData = new FormData(form);

    // Monta o JSON com os dados do formulário
    const data = {
        descricao: formData.get('descricao'),
        preco: parseFloat(formData.get('preco')),
        tamanho: formData.get('tamanho'),
        qtdeEstoque: parseInt(formData.get('qtdeEstoque'), 10)
    };

    try {
        const response = await fetch('/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                alert('Produto cadastrado com sucesso!');
                form.reset();
            } else {
                const error = await response.text();
                alert(`Erro ao cadastrar produto: ${error}`);
            }
    } catch (error) {
        alert(`Erro de rede: ${error}`);
    }
}


// Fazendo a requisição GET para o endpoint /products, para carregar produtos
    fetch('/products')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar os produtos.');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('productTableBody');
            tableBody.innerHTML = ''; // Limpa o conteúdo existente

            // Preenche a tabela com os produtos
            data.forEach(product => {
                const row = document.createElement('tr');
                row.id = `row-${product.idProduto}`;
                row.innerHTML = `
                    <td>
                    <button class="buttonExcluir" onclick="deleteRow('${product.idProduto}')">Excluir</button>
                    <button onclick="editRow('${product.idProduto}')">Editar</button>
                    <button onclick="saveRow('${product.idProduto}')" style="display: none;">Salvar</button>
                    </td>
                    <td>${product.idProduto}</td>
                    <td><input type="text" class="descricao" value="${product.descricao}" disabled></td>
                    <td><input type="number" step="0.01" class="preco" value="${product.preco}" disabled></td>
                    <td><input type="text" class="tamanho" value="${product.tamanho}" disabled></td>
                    <td><input type="number" class="qtdeEstoque" value="${product.qtdeEstoque}" disabled></td>
                    <td>
                    <button class="btnAddProdutoNoCarrinho" onclick="btnAddProdutoNoCarrinho(this)">Add Carrinho</button>
                    <div class="quantity-box hidden">
                    <p>Selecione a quantidade:</p>
                    <button class="quantity-option" data-qty="1">1</button>
                    <button class="quantity-option" data-qty="2">2</button>
                    <button class="quantity-option" data-qty="3">3</button>
                    <button class="quantity-option" data-qty="4">4</button>
                    <button class="quantity-option" data-qty="5">5</button>
                    <button class="quantity-option" data-qty="6">6</button>
                    <button class="quantity-option more-than-6">Mais de 6</button>
                    </div>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Não foi possível carregar os produtos.');
        });


// Abre a telinha de option/quant, transfere quant, preço, valorTotal para o form pedido
async function btnAddProdutoNoCarrinho(button) {
    let row = button.closest('tr');

    // Captura os valores da linha
    let idProduto = row.cells[1].textContent.trim();
    let preco = parseFloat(row.querySelector('.preco').value.trim()); // Converte para número

    let quantityBox = button.nextElementSibling;

    // Fecha outros quadros antes de abrir o atual
    document.querySelectorAll('.quantity-box').forEach(box => {
        if (box !== quantityBox) box.classList.add('hidden');
    });

    // Alterna exibição do quadro de opções
    quantityBox.classList.toggle('hidden');

    // Adiciona evento apenas se ainda não foi adicionado
    if (!quantityBox.dataset.eventsAdded) {
        quantityBox.dataset.eventsAdded = "true"; // Marca eventos adicionados

        quantityBox.querySelectorAll('.quantity-option').forEach(option => {
            option.addEventListener('click', function () {
                let selectedQty = parseInt(this.getAttribute('data-qty')) || 6; // Padrão "Mais de 6"

                let valorTotal = selectedQty * preco; // Multiplica quantidade × preço


                document.getElementById('idProduto').value = idProduto;
                document.getElementById('precoPdtPedido').value = preco;
                document.getElementById('qtdePedida').value = selectedQty;
                document.getElementById('valorTotal').value = valorTotal;

                // Esconde o quadro depois da seleção
                quantityBox.classList.add('hidden');

                // Redireciona com os valores na URL
                // window.location.href =
                // `http://localhost:8080/pedidos?idProduto=${idProduto}&quantidade=${selectedQty}&preco=${preco}&valorTotal=${valorTotal}`;

            });
        });
    }
}


// Fazendo a requisição GET para o endpoint /customers, (SELECT * FROM clientes)
function btnCarregarClientes() {
    fetch('/customers')
        .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao carregar os clientes.');
        }
        return response.json();
    })
        .then(data => {
        const tableBody = document.getElementById('clientesTableBody');
        tableBody.innerHTML = ''; // Limpa a tabela antes de adicionar novos dados

        // Preenche a tabela com os dados dos clientes
        data.forEach(cliente => {
            const row = document.createElement('tr');
            row.id = `row-${cliente.idCliente}`;
            row.innerHTML = `
                   <td><button onclick="btnSelecionarCliente('${cliente.idCliente}')">Selecionar</button></td>
                   <td>${cliente.idCliente}</td>
                   <td><input type="text" class="nomeCliente" value="${cliente.nomeCliente}" disabled></td>
                   <td><input type="text" class="sobreNomeCliente" value="${cliente.sobreNomeCliente}" disabled></td>
                   <td><input type="text" class="endereco" value="${cliente.endereco}" disabled></td>
                   <td><input type="text" class="telefone" value="${cliente.telefone}" disabled></td>
               `;
            tableBody.appendChild(row);
        });
    })
        .catch(error => {
        console.error('Erro:', error);
        alert('Não foi possível carregar os clientes.');
    });
}

function btnSelecionarCliente(idCliente) {
    const tableBody = document.getElementById('clientesTableBody');
    const rows = tableBody.getElementsByTagName('tr');

    // Oculta todas as linhas, exceto a selecionada
    for (let row of rows) {
        if (row.id !== `row-${idCliente}`) {
            row.style.display = 'none';
        }
    }
    // Define o valor do input hidden no formulário
    document.getElementById('idClienteInput').value = idCliente;
}

//Função para enviar form Pedido ( POST / INSERT )
async function submitFormPedido() {
    const form = document.getElementById('pedidoForm');
    const formData = new FormData(form);

    // Monta o JSON com os dados do formulário
    const data = {
        idCliente: formData.get('idCliente'),
        idProduto: formData.get('idProduto'),
        quant: parseInt(formData.get('qtdePedida'), 10),
        valorTotal: parseFloat(formData.get('valorTotal'))
    };

    try {
        const response = await fetch('/order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Pedido realizado  com sucesso!');
            form.reset();
        } else {
            const error = await response.text();
            alert(`Erro ao finalizar pedido: ${error}`);
        }
    } catch (error) {
        alert(`Erro de rede: ${error}`);
    }
}

// Função para excluir um produto / DELETE
function deleteRow(productId) {
    if (confirm("Tem certeza que deseja excluir este produto?")) {
        fetch(`/products/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
            if (response.ok) {
                alert("Produto excluído com sucesso!");
                // Remove a linha correspondente da tabela
                const row = document.querySelector(`[data-id="${productId}"]`);
                if (row) row.remove();
            } else {
                response.text().then(msg => alert(`Erro ao excluir: ${msg}`));
            }
        })
            .catch(error => alert(`Erro ao excluir: ${error.message}`));
    }
}

// Função para habilitar a edição
function editRow(id) {
    const row = document.getElementById(`row-${id}`);
    row.querySelectorAll('input').forEach(input => input.disabled = false);

    // Seleciona os botões diretamente pelo texto ou classe
    const editButton = row.querySelector('button[onclick^="editRow"]');
    const saveButton = row.querySelector('button[onclick^="saveRow"]');

    if (editButton && saveButton) {
        editButton.style.display = 'none'; // Oculta "Editar"
        saveButton.style.display = 'inline'; // Mostra "Salvar"
    }
}

// Função para salvar as alterações (PUT / UPDATE)
async function saveRow(id) {
    const row = document.getElementById(`row-${id}`);
    const descricao = row.querySelector('.descricao').value;
    const preco = parseFloat(row.querySelector('.preco').value);
    const tamanho = row.querySelector('.tamanho').value;
    const qtdeEstoque = parseInt(row.querySelector('.qtdeEstoque').value, 10);

    const updatedProduct = { descricao, preco, tamanho, qtdeEstoque };

    try {
        const response = await fetch(`/products/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedProduct),
        });

        if (response.ok) {
            alert('Produto atualizado com sucesso!');
            row.querySelectorAll('input').forEach(input => input.disabled = true);

            const editButton = row.querySelector('button[onclick^="editRow"]');
            const saveButton = row.querySelector('button[onclick^="saveRow"]');

            if (editButton && saveButton) {
                editButton.style.display = 'inline'; // Mostra "Editar"
                saveButton.style.display = 'none'; // Oculta "Salvar"
            }
        } else {
            const error = await response.text();
            alert(`Erro ao atualizar produto: ${error}`);
        }
    } catch (error) {
        alert(`Erro de rede: ${error}`);
    }
}


