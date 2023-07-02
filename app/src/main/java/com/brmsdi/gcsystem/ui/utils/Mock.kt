package com.brmsdi.gcsystem.ui.utils

import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.data.model.LocalizationCondominium
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.model.Status
import com.brmsdi.gcsystem.data.model.TypeProblem
import java.util.Date

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
            Status(1, "Aberto"),
            Status(2, "Disponível")
        )

        fun condominiumList() = mutableListOf(
            Condominium(
                1, "Vila Lobos 1", "xxxxxxxxxxxxxx", 20,
                statusList()[1], LocalizationCondominium()
            ),
            Condominium(
                2, "Vila Lobos 2", "yyyyyyyyyyyyyyyyy", 15,
                statusList()[1], LocalizationCondominium()
            )
        )

        fun listRepairRequestList(): MutableList<RepairRequest> {
            val list: MutableList<RepairRequest> = mutableListOf()
            for (i in 1000..1010) {
                val element = RepairRequest(
                    id = i,
                    problemDescription = "Tomadas da sala e dos quartos não estão funcionando",
                    date = Date(),
                    typeProblem = TypeProblem(1, "Elétrico"),
                    lessee = Lessee(),
                    condominium = Condominium(
                        1,
                        "Vila lobos ksdjskjdksdsfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdjkdjjskdjksjkdsjdaklsdjlajskjkdjasjdkasuyuueyrhjh",
                        "Desc-1",
                        50,
                        Status(1, "Aberto"),
                        LocalizationCondominium()
                    ),
                    status = Status(1, "Aberto"),
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
                    1, Date(), Date(), Date(), setOf(it), setOf(
                        Employee("Eliza"),
                        Employee("Michael")
                    ),
                    statusList()[1]
                )
            }

            return repairRequests
        }
    }
}