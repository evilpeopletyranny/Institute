package com.ngtu.sdp.laboratory_work2.aspects;

import com.ngtu.sdp.laboratory_work2.nodes.ContainerNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Класс содержащий методы сквозной логики - Логирование
 */
@Component
@Aspect
public class BuilderLoggerAspect {
    private static final String GRAPH_BUILDER_PREFIX = "Building a graph structure: ";

    //Экземпляр объекта логер библиотеки logf4
    private static final Logger LOGGER = LoggerFactory.getLogger(BuilderLoggerAspect.class);

    /******************************************   reset   *************************************************************/
    //Логгирование перед методом reset класса GraphBuilder
    @Before("execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.reset(..))")
    public void beforeResetLogger(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        LOGGER.debug(GRAPH_BUILDER_PREFIX + "building the root of the graph (class) - " + args[0] + ".");
    }

    //Логгирование при возращении объекта методом reset класса GraphBuilder
    @AfterReturning(value = "execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.reset(..))",
            returning = "node")
    public void afterReturningResetLogger(ContainerNode node) {
        if (node != null) LOGGER.debug(GRAPH_BUILDER_PREFIX + "root of the graph (class) - " + node.getData() + " - " +
                " built success!");
        else LOGGER.error(GRAPH_BUILDER_PREFIX + "graph root building error!");
    }

    /*********************************   toClassNodeAddClassNode   ****************************************************/
    @Before("execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toClassNodeAddClassNode(..))")
    //Логгирование перед методом toClassNodeAddClassNode класса GraphBuilder
    public void beforeToClassNodeAddClassNodeLogger(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        LOGGER.debug(GRAPH_BUILDER_PREFIX + "building subclass - " + args[1] + ".");
    }

    //Логгирование при возращении объекта методом toClassNodeAddClassNode класса GraphBuilder
    @AfterReturning(value = "execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toClassNodeAddClassNode(..))",
            returning = "node")
    public void afterReturningToClassNodeAddClassNodeLogger(ContainerNode node) {
        if (node != null) LOGGER.debug(GRAPH_BUILDER_PREFIX + "subclass - " + node.getData() + " - " +
                " built success!");
        else LOGGER.error(GRAPH_BUILDER_PREFIX + "subclass building error!");
    }

    /*******************************   toClassNodeAddIndividualNode   *************************************************/
    //Логгирование перед методом toClassNodeAddIndividualNode класса GraphBuilder
    @Before("execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toClassNodeAddIndividualNode(..))")
    public void beforeToClassNodeAddIndividualNodeLogger(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        LOGGER.debug(GRAPH_BUILDER_PREFIX + "building individual - " + args[1] + ".");
    }

    //Логгирование при возращении объекта методом toClassNodeAddIndividualNode класса GraphBuilder
    @AfterReturning(value = "execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toClassNodeAddIndividualNode(..))",
            returning = "node")
    public void afterReturningToClassNodeAddIndividualNodeLogger(ContainerNode node) {
        if (node != null) LOGGER.debug(GRAPH_BUILDER_PREFIX + "individual - " + node.getData() + " - " +
                " built success!");
        else LOGGER.error(GRAPH_BUILDER_PREFIX + "individual building error!");
    }

    /*****************************   toIndividualNodeAddAttributeNode   ***********************************************/
    //Логгирование перед методом toIndividualNodeAddAttributeNode класса GraphBuilder
    @Before("execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toIndividualNodeAddAttributeNode(..))")
    public void beforeToIndividualNodeAddAttributeNodeLogger(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        LOGGER.debug(GRAPH_BUILDER_PREFIX + "building attribute - " + args[1] + " with value = " + args[2] + ".");
    }

    //Логгирование при возращении объекта методом toIndividualNodeAddAttributeNode класса GraphBuilder
    @AfterReturning(value = "execution(* com.ngtu.sdp.laboratory_work2.builder.GraphBuilder.toIndividualNodeAddAttributeNode(..))",
            returning = "node")
    public void afterReturningToIndividualNodeAddAttributeNodeLogger(ContainerNode node) {
        if (node != null) LOGGER.debug(GRAPH_BUILDER_PREFIX + "attribute - " + node.getData() + " with value = "
                + node.getPropertyList().get(0).getChildNode().getData() + " - built success!");
        else LOGGER.error(GRAPH_BUILDER_PREFIX + "attribute building error!");
    }

    /************************************   Director.construct   ******************************************************/
    //Логгирование перед методом toIndividualNodeAddAttributeNode класса GraphBuilder
    @Before("execution(* com.ngtu.sdp.laboratory_work2.builder.Director.build(..))")
    public void beforeBuildingGraphLogger(JoinPoint joinPoint) {
        LOGGER.debug(GRAPH_BUILDER_PREFIX + "start graph building.");
    }

    @AfterReturning(value = "execution(* com.ngtu.sdp.laboratory_work2.builder.Director.build(..))",
            returning = "nodeOpt")
    public void afterReturningBuildingGraphLogger(Optional<ContainerNode> nodeOpt) {
        if (nodeOpt.isPresent()) LOGGER.debug(GRAPH_BUILDER_PREFIX + " graph build success.");
        else LOGGER.warn(GRAPH_BUILDER_PREFIX + "graph build canceled!");
    }
}
