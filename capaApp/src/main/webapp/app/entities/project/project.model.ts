import { Employee } from '../employee';
export class Project {
    constructor(
        public id?: number,
        public name?: string,
        public start?: any,
        public end?: any,
        public manager?: Employee,
        public employee?: Employee,
    ) {
    }
}
