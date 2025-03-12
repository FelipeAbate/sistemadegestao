
        // JAVA SCRIPT PARA CLIENTES


        //Função para preencher form Cadastro de Clientes
        async function submitForm() {
        const form = document.getElementById('clientesForm');
        const formData = new FormData(form);

        // Monta o JSON com os dados do formulário
        const data = {
            nomeCliente: formData.get('nomeCliente'),
            sobreNomeCliente: formData.get('sobreNomeCliente'),
            endereco: formData.get('endereco'),
            telefone: formData.get('telefone')
        };

        try {
            const response = await fetch('/customers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                alert('Cliente cadastrado com sucesso!');
                form.reset();
            } else {
                const error = await response.text();
                alert(`Erro ao cadastrar cliente: ${error}`);
            }
        } catch (error) {
            alert(`Erro de rede: ${error}`);
        }
    }


    // Fazendo a requisição GET para o endpoint /products, para carregar produtos
    fetch('/customers')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar os clientes.');
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('clientesTableBody');
            tableBody.innerHTML = ''; // Limpa o conteúdo existente

            // Preenche a tabela com os produtos
            data.forEach(cliente => {
                const row = document.createElement('tr');
                row.id = `row-${cliente.idCliente}`;
                row.innerHTML = `
                    <td>
                    <button class="buttonExcluir" onclick="deleteRow('${cliente.idCliente}')">Excluir</button>
                    <button onclick="editRow('${cliente.idCliente}')">Editar</button>
                    <button onclick="saveRow('${cliente.idCliente}')" style="display: none;">Salvar</button>
                    </td>
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
            alert('Não foi possível carregar clientes.');
        });


        // Função para excluir um cliente
                function deleteRow(clienteId) {
                    if (confirm("Tem certeza que deseja cliente?")) {
                        fetch(`/customers/${clienteId}`, {
                            method: 'DELETE',
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        })
                        .then(response => {
                            if (response.ok) {
                                alert("Cliente excluído com sucesso!");
                                // Remove a linha correspondente da tabela
                                const row = document.querySelector(`[data-id="${clienteId}"]`);
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


    // Função para salvar as alterações
    async function saveRow(id) {
        const row = document.getElementById(`row-${id}`);
        const nomeCliente= row.querySelector('.nomeCliente').value;
        const sobreNomeCliente = row.querySelector('.sobreNomeCliente').value;
        const endereco = row.querySelector('.endereco').value;
        const telefone = row.querySelector('.telefone').value;

        const updatedCliente = { nomeCliente, sobreNomeCliente, endereco, telefone };

        try {
            const response = await fetch(`/customers/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedCliente),
            });

            if (response.ok) {
                alert('Cliente atualizado com sucesso!');
                row.querySelectorAll('input').forEach(input => input.disabled = true);

                const editButton = row.querySelector('button[onclick^="editRow"]');
                const saveButton = row.querySelector('button[onclick^="saveRow"]');

                if (editButton && saveButton) {
                    editButton.style.display = 'inline'; // Mostra "Editar"
                    saveButton.style.display = 'none'; // Oculta "Salvar"
                }
            } else {
                const error = await response.text();
                alert(`Erro ao atualizar cliente: ${error}`);
            }
        } catch (error) {
            alert(`Erro de rede: ${error}`);
        }
    }
