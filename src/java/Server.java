import controller.*;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;


public class Server {

    static int PORT_NUM = 7000;

    public static void main(String[] args) {


        Spark.port(getPort());
        Spark.staticFiles.location("/public");

        Spark.get("/", (req, res) -> IndexController.indexController(), new VelocityTemplateEngine());

        Spark.get("/login", (req, res) -> LoginController.loginGet(req), new VelocityTemplateEngine());

        Spark.get("/index", (req, res) -> IndexController.loginGetController(req), new VelocityTemplateEngine());

        Spark.post("/index", IndexController::loginPostController);

        Spark.get("/individuals", (req, res) -> IndividualController.individualGet(), new VelocityTemplateEngine());

        Spark.get("/individualPage", (req, res) -> IndividualController.individualPageGet(), new VelocityTemplateEngine());


        Spark.get("/addindividuals", (req, res) -> IndividualController.addIndividualsGet(), new VelocityTemplateEngine());

        Spark.get("/editindividuals", (req, res) -> IndividualController.editIndividualsGet(), new VelocityTemplateEngine());

        Spark.post("/individuals", IndividualController::individualsPost);

        Spark.delete("/socialevents", EventsController::socialEventDelete);

        Spark.delete("/seminars", EventsController::seminarDelete);

        Spark.put("/individuals", IndividualController::individualUpdate);

        Spark.delete("/events", EventsController::eventDelete);

        Spark.get("/groups", (req, res) -> GroupsController.groupsGet(), new VelocityTemplateEngine());

        Spark.get("/addgroups", (req, res) -> GroupsController.addGroupsGet(), new VelocityTemplateEngine());

        Spark.post("/groups", GroupsController::groupsPost);

        Spark.get("/events", (req, res) -> EventsController.eventsGet(), new VelocityTemplateEngine());


        Spark.get("/addevents", (req, res) -> EventsController.addEventsGet(), new VelocityTemplateEngine());

        Spark.get("/socialevents", (req, res) -> EventsController.socialGet(), new VelocityTemplateEngine());

        Spark.get("/addsocialevents", (req, res) -> EventsController.addSocialGet(), new VelocityTemplateEngine());

        Spark.post("/socialevents", EventsController::socialPost);

        Spark.get("/seminars", (req, res) -> EventsController.seminarsGet(), new VelocityTemplateEngine());

        Spark.get("/addseminars", (req, res) -> EventsController.addSeminarsGet(), new VelocityTemplateEngine());

        Spark.post("/seminars", EventsController::seminarPost);

        Spark.get("/invitations", (req, res) -> JoinController.invitationsGet(), new VelocityTemplateEngine());

        Spark.post("/invitations", JoinController::invitationsPost);

        Spark.get("/memberships", (req, res) -> JoinController.membershipsGet(), new VelocityTemplateEngine());

        Spark.post("/memberships", (req, res) -> JoinController.membershipPost(req));

        Spark.get("/inviteindividual", (req, res) -> JoinController.inviteGet(), new VelocityTemplateEngine());

        Spark.get("/addmember", (req, res) -> JoinController.addMemberGet(), new VelocityTemplateEngine());

        Spark.post("/checkin", JoinController::checkInPost, new VelocityTemplateEngine());

        Spark.get("/checkin", (req, res) -> JoinController.checkInGet(), new VelocityTemplateEngine());

        Spark.get("/checkout", (req, res) -> JoinController.checkOutGet(), new VelocityTemplateEngine());

        Spark.post("/checkout", JoinController::checkOutPost);

        Spark.get("/findwhoscheckedin", (req, res) -> JoinController.invitedListGet(), new VelocityTemplateEngine());

        Spark.post("/findwhoscheckedin", (req, res) -> JoinController.invitedListPost(req));

        Spark.get("/findwhosinvited", (req, res) -> JoinController.invitedSearchGet(), new VelocityTemplateEngine());

        Spark.post("/findwhosinvited", (req, res) -> JoinController.invitedSearchPost(req));

        Spark.get("/geteventheat", (req, res) -> HeatController.heatGet(), new VelocityTemplateEngine());

        Spark.post("/geteventheat", (req, res) -> HeatController.heatPost(req));

        Spark.post("/gethottestevent", (req, res) -> HeatController.hottestPost());

        Spark.get("/gethottestevent", (req, res) -> HeatController.hottestGet(), new VelocityTemplateEngine());

        Spark.get("/filterbytag", (req, res) -> SearchController.filterTagGet(req), new VelocityTemplateEngine());

        Spark.get("/searchbytitleanddescription", (req, res) -> SearchController.filterText(req), new VelocityTemplateEngine());

        Spark.get("/addGroup", (req, res) -> GroupsController.addGroupGet(), new VelocityTemplateEngine());

        Spark.get("/invite", (req, res) -> JoinController.inviteToEventGet(), new VelocityTemplateEngine());

        Spark.get("/createEvent", (req, res) -> EventsController.createEventGet(), new VelocityTemplateEngine());

        Spark.get("/checkInEvent", (req, res) -> JoinController.checkInEventGet(), new VelocityTemplateEngine());

        Spark.get("/recommendedEvents", (req, res) -> IndividualController.recommendedEventsGet(), new VelocityTemplateEngine());

        Spark.post("/recommendedEvents", IndividualController::recommendedEventsPost, new VelocityTemplateEngine());
    }

    public static int getPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            PORT_NUM = Integer.parseInt(herokuPort);
        }
        return PORT_NUM;
    }
}