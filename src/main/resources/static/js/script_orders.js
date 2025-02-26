



// Fazendo a requisição GET para o endpoint /order
    // para carregar produtos
   fetch('/order')
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
               data.forEach(pedido => {
                   const row = document.createElement('tr');
                   row.id = `row-${pedido.idPedido}`;
                   row.innerHTML = `
                       <td>${pedido.idPedido}</td>
                       <td><input type="date" value="${pedido.dataPedido}" disabled></td>
                       <td><input type="number" class="quant" value="${pedido.quantidadePedida}" disabled></td>
                       <td>${pedido.idProduto}</td>

                   `;
                   tableBody.appendChild(row);
               });
           })
           .catch(error => {
               console.error('Erro:', error);
               alert('Não foi possível carregar os produtos.');
           });