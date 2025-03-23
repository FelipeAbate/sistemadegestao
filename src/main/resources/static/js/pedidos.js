
/*  Java Script Para PEDIDOS


//Recebe os parâmetros da URL para preencher o form automaticamente
document.addEventListener("DOMContentLoaded", function () {
    // Obtém os parâmetros da URL
    const urlParams = new URLSearchParams(window.location.search);
    const idProduto = urlParams.get("idProduto");
    const preco = urlParams.get("preco");
    const quantidade = urlParams.get("quantidade");
    const valorTotal = urlParams.get("valorTotal");

        // Se houver uma quantidade na URL, preenche o campo do formulário
        if (idProduto) {
              document.getElementById("idProduto").value = idProduto;
        }

        if (preco) {
              document.getElementById("preco").value = preco;
        }

        if (quantidade) {
              document.getElementById("quant").value = quantidade;
        }

        if (valorTotal) {
              document.getElementById("valorTotal").value = valorTotal;
        }

});


*/



// Fazendo a requisição GET para o endpoint /order, para carregar pedidos
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

            // Preenche a tabela com os pedidos detalhados
            data.forEach(pedido => {
                const row = document.createElement('tr');
                row.id = `row-${pedido.idPedido}`;
                row.innerHTML = `

                    <td><button class="buttonExcluir" onclick="deleteRow('${pedido.idPedido}')">Excluir</button></td>
                    <td>${pedido.idPedido}</td>
                    <td><input type="text" value="${pedido.dataCriacao}" disabled></td>
                    <td><input type="text" class="nomeCliente" value="${pedido.nomeCliente}" disabled></td>
                    <td><input type="text" class="descricao" value="${pedido.descricao}" disabled></td>
                    <td><input type="number" class="quant" value="${pedido.quant}" disabled></td>
                    <td><input type="number" step="0.01" class="preco" value="${pedido.preco}" disabled></td>
                    <td><input type="number" step="0.01" value="${pedido.valorTotal}" disabled></td>
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








