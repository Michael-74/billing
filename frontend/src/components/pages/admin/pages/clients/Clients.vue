<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client></app-create-client>
        </div>
        <div class="clients__items">
            <app-item-client :data="items"></app-item-client>
        </div>
    </div>
</template>

<script>
import FilterClients from './Filter'
import CreateClients from './Create'
import ItemClients from './Item'

export default {
    components: {
        AppFilterClient: FilterClients,
        AppCreateClient: CreateClients,
        AppItemClient: ItemClients,
    },
    data () {
        return {
            items: [
                {
                    id: 1,
                    ip: '192.168.0.1',
                    login: 'lenina_8_23',
                    balance: '534.53',
                    lastPriceDate: '23.10.2018',
                    lastPrice: '500',
                    contract: '132-1',
                    internet: 'kvartira_40',
                    tv: 'bandl_1',
                    rent: 'маршрутизатор',
                    promisePayment: 'Включен',
                    fio: 'Иванов Иван Иванович',
                    address: 'г. Копейск, пр-к Ленина, д.8, кв. 23',
                    phone: '89507234571',
                    email: 'michael74.ru@mail.ru',
                    typeDiscount: 'Максимальная скидка 10%',
                    discount: 7,
                    status: 'Активен',
                    loyalty: 8,
                    note: 'Клиента не было дома. Зайду в 14:00',
                    createAt: '11.01.2017',
                    updateAt: '12.11.2018'
                },
                {
                    id: 2,
                    ip: '192.168.0.2',
                    login: 'lenina_8_24',
                    balance: '234.53',
                    lastPriceDate: '13.11.2018',
                    lastPrice: '500',
                    contract: '132-1',
                    internet: 'kvartira_20',
                    tv: 'bandl_2',
                    rent: 'роутер',
                    promisePayment: 'Включен',
                    fio: 'Иванов Иван Иванович',
                    address: 'г. Копейск, пр-к Ленина, д.8, кв. 24',
                    phone: '89507234572',
                    email: 'michael74.ru@mail.ru',
                    typeDiscount: 'Максимальная скидка 20%',
                    discount: 13,
                    status: 'Активен',
                    loyalty: 2,
                    note: 'Клиента не было дома.',
                    createAt: '11.01.2017',
                    updateAt: '12.11.2018'
                },
                {
                    id: 3,
                    ip: '192.168.0.3',
                    login: 'lenina_1_24',
                    balance: '-1234.53',
                    lastPriceDate: '13.05.2018',
                    lastPrice: '-100',
                    contract: '132-1',
                    internet: 'kvartira_10',
                    tv: 'bandl_11',
                    rent: 'роутер',
                    promisePayment: 'Выключен',
                    fio: 'Иванов Иван Иванович',
                    address: 'г. Копейск, пр-к Ленина, д.1, кв. 24',
                    phone: '89507234573',
                    email: 'michael74.ru@mail.ru',
                    typeDiscount: 'Не выбрано',
                    discount: 0,
                    status: 'Выключен',
                    loyalty: 5,
                    note: '',
                    createAt: '11.05.2017',
                    updateAt: '12.11.2018'
                }
            ]
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
