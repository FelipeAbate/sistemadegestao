
        //SCRIPT PARA ESTOQUE


        //Função para preencher form Cadastro de Pedidos
        async function submitForm() {
        const form = document.getElementById('productForm');
        const formData = new FormData(form);

        // Monta o JSON com os dados do formulário
        const data = {
            descricao: formData.get('descricao'),
            preco: parseFloat(formData.get('preco')),
            quant: parseInt(formData.get('quant'), 10),
            tamanho: formData.get('tamanho')
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

    // Fazendo a requisição GET para o endpoint /products
    // para carregar produtos
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
                    <td>${product.idProduto}</td>
                    <td>
                    <button class="buttonExcluir" onclick="deleteRow('${product.idProduto}')">Excluir</button>
                    <button class="buttonAddCar" onclick="screenAddCar(this)">Add Carrinho</button>
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
                    <td><input type="text" class="descricao" value="${product.descricao}" disabled></td>
                    <td><input type="number" step="0.01" class="preco" value="${product.preco}" disabled></td>
                    <td><input type="number" class="quant" value="${product.quant}" disabled></td>
                    <td><input type="text" class="tamanho" value="${product.tamanho}" disabled></td>
                    <td>
                        <button onclick="editRow('${product.idProduto}')">Editar</button>
                        <button onclick="saveRow('${product.idProduto}')" style="display: none;">Salvar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Não foi possível carregar os produtos.');
        });

        // Função para excluir um produto
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

    //função para abrir telinha opções quantidade
    async function screenAddCar(button) {
        let quantityBox = button.nextElementSibling;

        // Fecha todos os outros quadros antes de abrir o atual
        document.querySelectorAll('.quantity-box').forEach(box => {
            if (box !== quantityBox) box.classList.add('hidden');
        });

        // Alterna a exibição do quadro de opções
        quantityBox.classList.toggle('hidden');

        // Adiciona evento apenas se ainda não foi adicionado
        if (!quantityBox.dataset.eventsAdded) {
            quantityBox.dataset.eventsAdded = "true"; // Marca que os eventos já foram adicionados

            quantityBox.querySelectorAll('.quantity-option').forEach(option => {
                option.addEventListener('click', function () {
                    let selectedQty = this.getAttribute('data-qty') || 'Mais de 6';
                    alert(`Você selecionou: ${selectedQty}`);

                    // Esconde o quadro depois da seleção
                    quantityBox.classList.add('hidden');
                });
            });
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


    // Função para salvar as alterações
    async function saveRow(id) {
        const row = document.getElementById(`row-${id}`);
        const descricao = row.querySelector('.descricao').value;
        const preco = parseFloat(row.querySelector('.preco').value);
        const quant = parseInt(row.querySelector('.quant').value, 10);
        const tamanho = row.querySelector('.tamanho').value;

        const updatedProduct = { descricao, preco, quant, tamanho };

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



