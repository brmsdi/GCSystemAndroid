package com.brmsdi.gcsystem.ui.utils

import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelWithTokenDTO
import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.data.model.LocalizationCondominium
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.model.Status
import com.brmsdi.gcsystem.data.model.TypeProblem
import java.util.Date
import javax.net.ssl.HttpsURLConnection

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class Mock {

    companion object {
        fun typeProblemList() = mutableListOf(
            TypeProblem(1, "Problema 1"),
            TypeProblem(2, "Problema 2"),
            TypeProblem(3, "Problema 3")
        )

        fun statusList() = mutableListOf(
            Status(1, "Em aberto"),
            Status(2, "Disponível"),
            Status(3, "Em andamento"),
            Status(4, "Concluído")
        )

        private fun itemList() = mutableListOf(
            Item(1, "Tomadas", 10, 12.00),
            Item(2, "Tomadas 2", 1, 50.00)
        )

        fun condominiumList() = mutableListOf(
            Condominium(
                1, "Vila Lobos 1", "xxxxxxxxxxxxxx", 20,
                statusList()[1], LocalizationCondominium(1)
            ),
            Condominium(
                2, "Vila Lobos 2", "yyyyyyyyyyyyyyyyy", 15,
                statusList()[1], LocalizationCondominium(1)
            )
        )

        private fun employeesList(): MutableList<Employee> {
            return mutableListOf(
                Employee("Eliza"),
                Employee("Wisley")
            )
        }

        fun lesseeList(): MutableList<Lessee> {
            return mutableListOf(
                Lessee("Patrícia"),
                Lessee("Debora")
            )
        }

        fun listRepairRequestList(): MutableList<RepairRequest> {
            val list: MutableList<RepairRequest> = mutableListOf()
            for (i in 1000..1010) {
                val element = RepairRequest(
                    id = i,
                    problemDescription = "Tomadas da sala e dos quartos não estão funcionando",
                    date = Date(),
                    typeProblem = typeProblemList()[1],
                    lessee = lesseeList()[0],
                    condominium = condominiumList()[1],
                    status = statusList()[2],
                    items = null,
                    apartmentNumber = "103",
                    orderService = null
                )
                list.add(element)
            }
            return list
        }

        fun listRepairRequestListWithOrderService(): MutableList<RepairRequest> {
            val repairRequests = listRepairRequestList()
            repairRequests.forEach {
                it.orderService = OrderService(
                    1, Date(), Date(), Date(), mutableListOf(it), employeesList().toSet(),
                    statusList()[1]
                )
            }

            return repairRequests
        }

        fun responseRequestDTOList(): MutableList<ResponseRequestDTO> {
            val responseRequestDTO: MutableList<ResponseRequestDTO> = mutableListOf()
            responseRequestDTO.add(
                ResponseRequestDTO().apply {
                    status = HttpsURLConnection.HTTP_OK
                    code = "123456"
                }
            )
            return responseRequestDTO
        }

        fun getTokenDTO(): TokenDTO {
            val tokenDTO = TokenDTO()
            tokenDTO.apply {
                type = ""
                token = "token123456789"
            }
            return tokenDTO
        }

        fun getValidationModelWithTokenDTO(): ValidationModelWithTokenDTO {
            return ValidationModelWithTokenDTO(getTokenDTO())
        }

        fun listOrderService(): MutableList<OrderService> {
            val list: MutableList<OrderService> = mutableListOf();
            for (i in 2000..2002) {
                val element = OrderService(
                    id = i,
                    generationDate = Date(),
                    reservedDate = null,
                    completionDate = null,
                    mutableListOf(listRepairRequestList()[0], listRepairRequestList()[1], listRepairRequestList()[2]),
                    setOf(employeesList()[0]),
                    statusList()[0]
                )
                element.repairRequests.forEach {repair ->
                    repair.items = itemList().map { item -> Item(item.id + repair.id, item.description, item.quantity, item.value ) }.toMutableList()
                }
                if (element.id == 2000) {
                    val concluded = statusList()[3]
                    element.status = concluded
                    element.repairRequests.forEach { it.status = concluded }
                }
                list.add(element)
            }
            return list
        }

    }
}