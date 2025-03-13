
// Java Script Para PEDIDOS


// Fazendo a requisição GET para o endpoint /order, para carregar produtos
   fetch('/order/details')
           .then(response => {
               if (!response.ok) {
                   throw new Error('Erro ao carregar os pedidos.');
               }
               return response.json();
           })
           .then(data => {
               const tableBody = document.getElementById('pedidosTableBody');
               tableBody.innerHTML = ''; // Limpa o conteúdo existente

               // Preenche a tabela com os produtos
               data.forEach(pedido => {
                   const row = document.createElement('tr');
                   row.id = `row-${pedido.idPedido}`;
                   row.innerHTML = `

                       <td><button class="buttonExcluir" onclick="deleteRow('${pedido.idPedido}')">Excluir</button></td>
                       <td>${pedido.idPedido}</td>
                       <td><input type="date" value="${pedido.dataCriacao}" disabled></td>
                       <td><input type="text" class="nomeCliente" value="${pedido.nomeCliente}" disabled></td>
                       <td><input type="text" class="descricao" value="${pedido.descricao}" disabled></td>
                       <td><input type="number" class="quant" value="${pedido.quant}" disabled></td>
                       <td><input type="number" step="0.01" class="preco" value="${pedido.preco}" disabled></td>
                   `;
                   tableBody.appendChild(row);
               });
           })
           .catch(error => {
               console.error('Erro:', error);
               alert('Não foi possível carregar os pedidos.');
           });


// Função para excluir um produto
                function deleteRow(pedidoId) {
                    if (confirm("Tem certeza que deseja excluir este pedido?")) {
                        fetch(`/order/${pedidoId}`, {
                            method: 'DELETE',
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        })
                        .then(response => {
                            if (response.ok) {
                                alert("Pedido excluído com sucesso!");
                                // Remove a linha correspondente da tabela
                                const row = document.querySelector(`[data-id="${pedidoId}"]`);
                                if (row) row.remove();
                            } else {
                                response.text().then(msg => alert(`Erro ao excluir: ${msg}`));
                            }
                        })
                        .catch(error => alert(`Erro ao excluir: ${error.message}`));
                    }
                }



