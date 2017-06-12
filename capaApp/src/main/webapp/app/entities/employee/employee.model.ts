
const enum Country {
    'GERMANY',
    'INDIA',
    'POLAND'

};

const enum Team {
    'OSC',
    'NSC',
    'MUM'

};
import { Project } from '../project';
export class Employee {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public dayRate?: number,
        public shortName?: string,
        public shore?: string,
        public grade?: string,
        public projectAvailability?: number,
        public internProjectAvailability?: number,
        public formDate?: any,
        public toDate?: any,
        public capacityPA?: number,
        public capacityIPA?: number,
        public country?: Country,
        public team?: Team,
        public project?: Project,
    ) {
    }
}
